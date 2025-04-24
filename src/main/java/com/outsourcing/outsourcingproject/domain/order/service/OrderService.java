package com.outsourcing.outsourcingproject.domain.order.service;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.domain.order.dto.OrderResponseDto;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	/*
	1. 주문 생성
	2. 주문 수락, 거절
	3. 주문 단건 조회
	 */

	// 1. 주문 생성
	@Transactional
	public OrderResponseDto createOrder() {
		
	}
}
