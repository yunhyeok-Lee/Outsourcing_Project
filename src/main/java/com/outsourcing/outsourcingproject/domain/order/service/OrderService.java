package com.outsourcing.outsourcingproject.domain.order.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderRequestDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderResponseDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderStatusResponseDto;
import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.entity.OrderEntities;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.entity.StoreStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final EntityFetcher entityFetcher;
	private final JwtUtil jwtUtil;

    /*
    1. 주문 요청 생성
    2. 주문 수락/거절
    3. 주문 단건 조회
    4. 주문 요청 취소
     */

	// 1. 주문 생성
	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, String token) {

		Long userId = jwtUtil.getUserIdFromToken(token);

		// 주문 요청 중복 확인
		if (orderRepository.findOrderByUserId(userId).isPresent()) {
			throw new CustomException(ErrorCode.ORDER_REQUEST_ALREADY_SENT);
		}

		Store store = entityFetcher.getStoreOrThrow(orderRequestDto.getStoreId());
		LocalTime open = store.getOpenTime();
		LocalTime close = store.getCloseTime();
		LocalTime now = LocalTime.now();

		// 가게 OPEN 상태일 때만 주문 가능
		if (!entityFetcher.storeStatus(open, close, now).equals(StoreStatus.OPEN)) {
			throw new CustomException(ErrorCode.STORE_NOT_OPEN);
		}

		// 엔티티 조회
		OrderEntities entities = entityFetcher.fetchOrderEntities(userId, orderRequestDto);

		// 새로운 주문 생성
		Order order = Order.builder()
			.user(entities.user())
			.store(entities.store())
			.menu(entities.menu())
			.deliveryStatus(DeliveryStatus.WAITING)
			.build();

		orderRepository.save(order);

		return new OrderResponseDto(order);

	}

	// 2. storeId 로 주문 목록 조회 with 상태
	@Transactional
	public List<OrderResponseDto> getOrderList(Long storeId) {

		// storeId 로 store 객체를 하나 가져온다. 없으면 예외 던진다
		Store store = entityFetcher.getStoreOrThrow(storeId);

		// 가져온 store 에 있는 모든 주문을 DB 에서 가져온다 → orderList 안에 Order 객체들이 담긴다
		List<Order> orderList = orderRepository.findAllByStore(store);

		// OrderResponseDto 타입으로 결과를 담을 빈 리스트를 만든다
		List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

		for (Order order : orderList) {
			OrderResponseDto orderResponseDto = new OrderResponseDto(order);
			orderResponseDtoList.add(orderResponseDto);
		}
		return orderResponseDtoList;
	}

	// 3. 주문 상태 변경 API
	@Transactional
	public OrderStatusResponseDto handleRequest(Long orderId, DeliveryStatus deliveryStatus, String token) {
		Order order = entityFetcher.getOrderOrThrow(orderId);

		// 사용자가 보낸 deliveryStatus 로 Order 엔티티 수정
		order.updateDeliveryStatus(deliveryStatus);
		return new OrderStatusResponseDto(orderId, order.getStore().getId(), deliveryStatus, LocalTime.now());
	}

	// 4. 주문 취소
	@Transactional
	public void deleteOrder(Long orderId) {
		Order order = entityFetcher.getOrderOrThrow(orderId);
		orderRepository.delete(order);
	}

}
