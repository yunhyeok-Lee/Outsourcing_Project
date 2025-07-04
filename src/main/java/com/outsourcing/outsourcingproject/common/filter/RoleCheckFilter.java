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

		// 요청 메서드를 확인
		String method = httpRequest.getMethod();

		// JwtAuthenticationFilter 에서 setAttribute("authority") 했던거를 꺼내옴.
		String role = (String)httpRequest.getAttribute("authority");
		String uri = httpRequest.getRequestURI();
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json");

		// 예: 메뉴 생성/수정은 OWNER 만 허용
		if ((uri.endsWith("/menu")) && !"OWNER".equals(role)) {
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			httpResponse.getWriter().write("사장님만 접근할 수 있습니다.");
			return;
		}

		// Store
		if (!"GET".equalsIgnoreCase(method)) { // 1차 조건 : 가게 조회가 아닌 경우에만
			if (uri.startsWith("/stores") && !"OWNER".equals(role)) { // 2차 조건 : uri 가 stores 로 시작하고 사장님이 아닌 경우에만
				httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				httpResponse.getWriter().write("사장님만 접근할 수 있습니다.");
				return;
			}
		}

		// USER 만 주문 생성 가능
		if ("POST".equalsIgnoreCase(method)) {
			if (uri.endsWith("/orders") && !"USER".equals(role)) {
				httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				httpResponse.getWriter().write("사용자만 주문을 생성할 수 있습니다.");
				return;
			}
		}

		// OWNER 만 배달 상태 변경 가능
		if ("PATCH".equalsIgnoreCase(method)) {
			if (uri.startsWith("/orders/{id}") && !"OWNER".equals(role)) {
				httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				httpResponse.getWriter().write("사장님만 상태 변경할 수 있습니다.");
				return;
			}
		}

		chain.doFilter(request, response); // 통과
	}
}
