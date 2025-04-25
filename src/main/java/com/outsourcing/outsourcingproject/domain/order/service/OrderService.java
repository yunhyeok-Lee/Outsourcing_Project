package com.outsourcing.outsourcingproject.domain.order.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderByMenuResponseDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderByStoreResponseDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderByUserResponseDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderRequestDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderResponseDto;
import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	/*
	1. 주문 요청 생성
	2. 주문 수락, 거절
	3. 주문 단건 조회
	4. 주문 요청 취소
	 */

	// 1. 주문 생성
	@Transactional
	public OrderResponseDto createOrder(Long orderId, OrderRequestDto orderRequestDto) {
		// 주문 요청 중복 확인
		if (orderRepository.findOrderByUserId(orderId, orderRequestDto.getUserId()).isPresent()) {
			throw new CustomException(ErrorCode.ORDER_REQUEST_ALREADY_SENT);
		}

		// 새로운 주문 생성
		Order order = Order.builder()
			.user(user)
			.store(store)
			.menu(menu)
			.deliveryStatus(DeliveryStatus.WAITING)
			.build();

		orderRepository.save(order);

		return new OrderResponseDto(order);

	}

	// 2. 주문 수락, 거절
	@Transactional
	public String handleRequest(Long orderId, String action) {

		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

		/* ✏️
		action.toUpperCase() : 문자열을 모두 대문자로 변환해주는 Java 메서드
		"confirm" → "CONFIRMED"
		"Reject" → "REJECT"
		"wAiTiNg" → "WAITING"
		 */
		switch (action.toUpperCase()) {
			case "WAITING":
				order.waiting();
				return "주문 접수 대기 상태입니다.";
			case "CONFIRMED":
				order.confirm();
				return "주문이 접수되었습니다.";
			case "REJECTED":
				order.reject();
				return "주문이 접수가 거절되었습니다.";
			default:
				throw new CustomException(ErrorCode.INVALID_ORDER_REQUEST);
		}
	}

	// 3. 주문 단건 조회
	@Transactional
	public void getConfirmedOrder(
		Long storeId,
		OrderByUserResponseDto userDto,
		OrderByStoreResponseDto storeDto,
		OrderByMenuResponseDto menuDto) {

		return orderRepository.findByStoreIdAndStatus(storeId, DeliveryStatus.CONFIRMED)
			.stream()
			.map(OrderByUserResponseDto::new)
			.map(OrderByStoreResponseDto::new)
			.map(OrderByMenuResponseDto::new)
			.collect(Collectors.toList());
	}

	// 4. 주문 취소
	@Transactional
	public String cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
		orderRepository.delete(order);
		return "주문 요청이 취소되었습니다.";
	}

}
