package com.outsourcing.outsourcingproject.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.outsourcing.outsourcingproject.domain.order.dto.OrderStatusResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class OrderLogAop {

	// Service 클래스에 handleRequest 메서드(매개변수 상관 없이)를 대상으로 한다
	@Pointcut("execution(* com.outsourcing.outsourcingproject.domain.order..Service.handleRequest(..))")
	private void controller() {
	}

	// 포인트컷으로 설정된 메서드 실행 전후에 로그를 출력하는 Around Advice
	@AfterReturning
	public Object logginBefore(ProceedingJoinPoint joinPoint, OrderStatusResponseDto dto) throws Throwable {

		// 현재 HTTP 요청을 가져온다
		// (ServletRequestAttributes)RequestContextHolder.getRequestAttributes() : 요청 데이터를 전역(global)에서 가져오기
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

		// null 일 경우를 대비하여 null 인 경우에는 경고 로그를 출력
		if (requestAttributes == null) {
			log.warn("RequestAttributes가 null 압나다.");
			return joinPoint.proceed();
		}

		log.info("주문 id:" + dto.getId());
		log.info("가게 id:" + dto.getStoreId());
		log.info("주문 상태:" + dto.getDeliveryStatus());
		log.info("요청 시각:" + dto.getUpdatedAt());

		return joinPoint.proceed();
	}
}
