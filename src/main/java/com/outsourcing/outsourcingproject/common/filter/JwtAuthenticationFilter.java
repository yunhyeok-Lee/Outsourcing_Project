package com.outsourcing.outsourcingproject.common.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;

import org.springframework.util.PatternMatchUtils;

import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

	private String secretKey = "dGhpc2lzMjZ0ZWFtc291dHNvdXJjaW5ncHJvamVjdHNlY3JldGtleQ==";
	byte[] bytes = Base64.getDecoder().decode(secretKey);
	private Key key = Keys.hmacShaKeyFor(bytes);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;

		String requestURI = httpRequest.getRequestURI();

		// 요청 Header에서 "Authorization" 이라는 토큰을 꺼낸다.
		String authHeader = httpRequest.getHeader("Authorization");

		if (!isWhiteList(requestURI) && authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {

				// userId 추출
				String userId = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject(); // userId

				// authority 추출
				String authority = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody()
					.get("authority", String.class); // authority
				// 정보를 request에 저장
				httpRequest.setAttribute("userId", userId);
				httpRequest.setAttribute("authority", authority);

				// 유효성 검사 + 정보 꺼냄 [ 미사용 ]
				// Long userId = jwtUtil.getUserIdFromToken(token);
				// String authority = jwtUtil.getAuthorityFromToken(token);

				httpResponse.setCharacterEncoding("UTF-8");
				httpResponse.setContentType("application/json");
				chain.doFilter(request, response); // 다음 필터로
				return;

				// 토큰에 문제가 있다면 401, UNAUTHORIZED 응답
			} catch (CustomException e) {
				e.printStackTrace();
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpResponse.getWriter().write("Invalid token");
				return;
			}
			// header 가 없는 경우 401, UNAUTHORIZED 응답
		} else if (isWhiteList(requestURI)) {
			chain.doFilter(request, response);
		} else {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.getWriter().write("Missing Authorization header");
		}
	}
}
