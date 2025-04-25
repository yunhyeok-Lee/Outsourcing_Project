package com.outsourcing.outsourcingproject.common.util;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.outsourcing.outsourcingproject.common.exception.CustomException;

public class JwtAuthenticationFilter implements Filter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;

		String authHeader = httpRequest.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				// 유효성 검사 + 정보 꺼냄
				Long userId = jwtUtil.getUserIdFromToken(token);
				String authority = jwtUtil.getAuthorityFromToken(token);

				// 정보를 request에 저장
				httpRequest.setAttribute("userId", userId);
				httpRequest.setAttribute("authority", authority);

				chain.doFilter(request, response); // 다음 필터로
				return;

			} catch (CustomException e) {
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpResponse.getWriter().write("Invalid token");
				return;
			}

		} else {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.getWriter().write("Missing Authorization header");
		}
	}
}
