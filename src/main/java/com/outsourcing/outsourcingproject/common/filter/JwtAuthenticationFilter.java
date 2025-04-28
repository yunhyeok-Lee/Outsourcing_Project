package com.outsourcing.outsourcingproject.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.springframework.util.PatternMatchUtils;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.user.entity.Authority;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter implements Filter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	private static final String[] WHITE_LIST = {
		"/users",
		"/users/login"};

	private boolean isWhiteList(String requestURI) {
		return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;

		String requestURI = httpRequest.getRequestURI();
		String method = httpRequest.getMethod();

		// 화이트리스트면 다음 필터 수행
		if (isWhiteList(requestURI) && "POST".equals(method)) {
			chain.doFilter(request, response);
			return;
		}

		// 요청 헤더에서 access token 추출
		String accessToken = httpRequest.getHeader("Authorization");

		// 토큰이 없으면 예외처리
		if (accessToken == null) {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.getWriter().write("Missing Authorization header");
			return;
		}

		Long userId = null;
		String authority = null;

		try {
			// access 토큰에서 정보 추출 & 토큰 서명 위조 여부 검증
			userId = jwtUtil.getUserIdFromToken(accessToken);
			authority = jwtUtil.getAuthorityFromToken(accessToken);

			// access token 만료 시, refresh token 검증
		} catch (ExpiredJwtException e) {
			// 요청 헤더에서 refreshToken 추출
			String refreshToken = Arrays.stream(httpRequest.getCookies())
				.filter(cookie -> "refreshToken".equals(cookie.getName()))
				.findFirst()
				.map(Cookie::getValue)
				.orElseThrow(() -> new CustomException(ErrorCode.INVALID_SIGNATURE));

			// refresh token 만료 시 예외처리
			if (jwtUtil.getRefreshExpiration(refreshToken).before(new Date())) {
				throw new CustomException(ErrorCode.INVALID_SIGNATURE);
			}

			// access token 재발급
			String newAccessToken = jwtUtil.createAccessToken(userId, Authority.valueOf(authority));
			httpResponse.setHeader("Authorization", newAccessToken);
		}

		// 정보를 request 에 저장해서 다음 필터로 전달
		httpRequest.setAttribute("userId", userId);
		httpRequest.setAttribute("authority", authority);

		// 응답 설정
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json");

		chain.doFilter(request, response);
	}
}
