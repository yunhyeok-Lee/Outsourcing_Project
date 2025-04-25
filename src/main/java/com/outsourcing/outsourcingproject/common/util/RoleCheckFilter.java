package com.outsourcing.outsourcingproject.common.util;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RoleCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;

		String role = (String)httpRequest.getAttribute("authority");
		String uri = httpRequest.getRequestURI();

		// 예: 메뉴 생성/수정은 OWNER만 허용
		if ((uri.startsWith("/api/menu")) && !"OWNER".equals(role)) {
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			httpResponse.getWriter().write("Forbidden: Only owner can access");
			return;
		}

		chain.doFilter(request, response); // 통과
	}
}
