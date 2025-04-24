package com.outsourcing.outsourcingproject.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.outsourcing.outsourcingproject.common.config.JwtUtil;
import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {
	private final JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		String token = request.getHeader("Authorization");
		String uri = request.getRequestURI();
		String method = request.getMethod();

		// 화이트리스트에 포함된 "/users" 중 POST method만 통과시키기(회원가입만)
		if ("/users".equals(uri) && "POST".equals(method)) {
			return true;
		}

		// 토큰이 없으면 예외(StringUtils.hasNext()는 NPE 방지)
		if (!StringUtils.hasText(token)) {
			throw new CustomException(ErrorCode.INVALID_SIGNATURE);
		}

		// 컨트롤러에서 데이터를 꺼내오기 쉽도록 request에 setting
		Claims claims = jwtUtil.extractClaims(token);
		request.setAttribute("userId", claims.getSubject());
		request.setAttribute("authority", claims.get("authority", String.class));

		return true;
	}

	// @Override
	// public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	// 	ModelAndView modelAndView) throws Exception {
	// 	// 로그아웃, 회원탈퇴 request의 경우 expiration을 0으로 설정
	// 	if ("/users/logout".equals(request.getRequestURI()) || "DELETE".equals(request.getMethod())) {
	// 		String token = request.getHeader("Authorization");
	// 		Claims claims = jwtUtil.extractClaims(token);
	// 		claims.setExpiration(new Date());
	// 	}
	// }
}
