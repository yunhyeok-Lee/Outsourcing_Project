package com.outsourcing.outsourcingproject.common.filter;

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
		String method = httpRequest.getMethod();

		// JwtAuthenticationFilter에서 setAttribute("authority") 했던거를 꺼내옴.
		String role = (String)httpRequest.getAttribute("authority");
		String uri = httpRequest.getRequestURI();

		// 예: 메뉴 생성/수정은 OWNER만 허용
		if ((uri.startsWith("/menus")) && !"OWNER".equals(role)) {
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			httpResponse.getWriter().write("Forbidden: 사장님만 접근할 수 있습니다.");
			return;
		}

		if (uri.startsWith("/stores") && !"OWNER".equals(role)) {
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			httpResponse.getWriter().write("Forbidden: 사장님만 가게를 생성할 수 있습니다.");
			return;
		}

		if (uri.startsWith("/orders") && !"USER".equals(role)) {
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			httpResponse.getWriter().write("Forbidden: 사용자만 주문을 생성할 수 있습니다.");
			return;
		}

		if ("PATCH".equalsIgnoreCase(method)) {
			if (uri.startsWith("/orders/{id}") && !"OWNER".equals(role)) {
				httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				httpResponse.getWriter().write("Forbidden: 사장님만 상태 변경할 수 있습니다.");
				return;
			}
		}

		chain.doFilter(request, response); // 통과
	}
}
