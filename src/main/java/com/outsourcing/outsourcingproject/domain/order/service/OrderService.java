package com.outsourcing.outsourcingproject.domain.order.service;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	/*
	1. 주문 생성
	2. 주문 수락, 거절
	 */

}
