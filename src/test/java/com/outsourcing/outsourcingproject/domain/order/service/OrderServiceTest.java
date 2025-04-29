package com.outsourcing.outsourcingproject.domain.order.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderRequestDto;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private EntityFetcher entityFetcher;

	@InjectMocks
	private OrderService orderService;

	@Test
	void 중복으로_접수된_주문이면_CustomException() {

		// given
		Long userId = 1L;
		String token = "mockToken";
		Long storeId = 10L;
		Long menuId = 100L;
		OrderRequestDto orderRequestDto = new OrderRequestDto(storeId, menuId);

		/*
		jwtUtil.getUserIdFromToken(token) ➔ userId 뽑아야 하고

		orderRepository.findOrderByUserId(userId) ➔ 이미 주문 존재하는 상황 만들기

		그런 다음 CustomException 이 터지는지 검증하기
		 */
		// Stubbing
		when(jwtUtil.getUserIdFromToken(token)).thenReturn(userId);
		when(orderRepository.findOrderByUserId(userId)).thenReturn(Optional.of(mock(Order.class)));

		// when & then
		assertThatThrownBy(() -> orderService.createOrder(orderRequestDto, token))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining("이미 주문이 접수되었습니다.");
	}

	@Test
	void 주문_존재시_deleteOrder_검증() {

		// given: 취소하려는 주문의 ID
		Long orderId = 1L;

		// Stubbing: 주문 객체를 Mock 으로 생성
		Order mockOrder = mock(Order.class);

		// EntityFetcher 의 getOrderOrThrow 메서드 호출될 때, mockOrder 를 반환하도록 설정
		when(entityFetcher.getOrderOrThrow(orderId)).thenReturn(mockOrder);

		// when: 주문 취소 메서드 호출
		orderService.deleteOrder(orderId);

		// then: OrderRepository.delete()가 한 번 호출되었는지 검증
		verify(orderRepository, times(1)).delete(mockOrder);
	}

	@Test
	void 주문_미존재시_deleteOrder하면_CustomException() {

		// given: 존재하지 않는 주문 ID
		Long orderId = 999L;

		// Stubbing: 주문이 존재하지 않으면 CustomException 발생
		when(entityFetcher.getOrderOrThrow(orderId)).thenThrow(CustomException.class);

		// when & then: 예외가 발생하는지 검증
		assertThatThrownBy(() -> orderService.deleteOrder(orderId))
			.isInstanceOf(CustomException.class);
	}

}
