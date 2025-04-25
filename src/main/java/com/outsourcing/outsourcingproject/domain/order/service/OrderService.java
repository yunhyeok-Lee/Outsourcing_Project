package com.outsourcing.outsourcingproject.domain.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderRequestDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderResponseDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderStatusResponseDto;
import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final EntityFetcher entityFetcher;

	/*
	1. 주문 요청 생성
	2. 주문 수락/거절
	3. 주문 단건 조회
	4. 주문 요청 취소
	 */

	// 1. 주문 생성
	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
		// 주문 요청 중복 확인
		if (orderRepository.findOrderByUserId(orderRequestDto.getUserId()).isPresent()) {
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

	// 2. 주문 상태 변경
	@Transactional
	public OrderStatusResponseDto handleRequest(Long orderId, DeliveryStatus deliveryStatus) {
		Order order = entityFetcher.getOrderOrThrow(orderId);

		// 사용자가 보낸 딜리버리스테이터스로 오더 엔티티 수정
		order.updateDeliveryStatus(deliveryStatus);
		return new OrderStatusResponseDto(orderId, deliveryStatus);
	}

	// 3. storeId 로 주문 목록 조회 with 상태
	@Transactional
	public List<Order> getConfirmedOrder() {
		return null;
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
