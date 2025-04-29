package com.outsourcing.outsourcingproject.common.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderEntityFetcherTest {

	/* ✏️
	1. given: 주어진 상황
	2. when: 행동
	3. then: 검증
	 */

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private EntityFetcher entityFetcher;

	@Test
	void 일치하는_주문id_없으면_CustomException() {

		// given
		Long id = 1L;

		// Stubbing
		when(orderRepository.findById(id)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> entityFetcher.getOrderOrThrow(id))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining("접수되지 않은 주문 번호입니다.");
	}
}
