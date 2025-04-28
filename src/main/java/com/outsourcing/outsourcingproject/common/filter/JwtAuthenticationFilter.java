package com.outsourcing.outsourcingproject.common.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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

		// 요청 Header에서 "Authorization"에 저장된 토큰을 꺼낸다.
		String authHeader = httpRequest.getHeader("Authorization");

		if (authHeader != null) {
			try {
				//Todo: Access Token

				//유효성 검사 + 정보 꺼냄
				Long userId = jwtUtil.getUserIdFromToken(authHeader);
				String authority = jwtUtil.getAuthorityFromToken(authHeader);

				// 정보를 request에 저장해서 다음 필터로 전달
				httpRequest.setAttribute("userId", userId);
				httpRequest.setAttribute("authority", authority);

				// 응답 설정
				httpResponse.setCharacterEncoding("UTF-8");
				httpResponse.setContentType("application/json");

				// 다음 필터 수행
				chain.doFilter(request, response);

				// 토큰에 문제가 있다면 401, UNAUTHORIZED 응답
			} catch (CustomException e) {
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpResponse.getWriter().write("Invalid token");
			}
		} else { // 토큰 없음
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.getWriter().write("Missing Authorization header");
		}
	}
}
