package com.outsourcing.outsourcingproject.domain.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.common.dto.CommonResponse;
import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderRequestDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderResponseDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderStatusRequestDto;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderStatusResponseDto;
import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	/*
	1. 주문 요청 생성
	2. 주문 수락/거절
	3. 주문 단건 조회
	4. 주문 요청 취소
	 */

	// 1. 주문 요청 생성 API (사용자만 권한 있음)
	@PostMapping("/orders")
	public CommonResponse<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto requestDto,
		@RequestHeader("Authorization") String token) {
		return CommonResponse.of(SuccessCode.SENDING_ORDER_SUCCESS,
			orderService.createOrder(requestDto, token));
	}

	// 2. storeId 로 주문 목록 조회 with 상태
	@GetMapping("/{storeId}/orders")
	public CommonResponse<List<OrderResponseDto>> findOrderByStoreId(@PathVariable Long storeId) {
		return CommonResponse.of(SuccessCode.GET_ORDER_LIST_SUCCESS, orderService.getOrderList(storeId));
	}

	// 3. 주문 상태 변경 API (사장님만 권한 있음)
	@PatchMapping("/orders/{id}")
	public CommonResponse<OrderStatusResponseDto> handleRequest(
		@PathVariable Long id,
		@RequestBody @Valid OrderStatusRequestDto orderStatusRequestDto,
		@RequestHeader("Authorization") String token) {
		DeliveryStatus deliveryStatus = orderStatusRequestDto.getDeliveryStatus();
		return CommonResponse.of(SuccessCode.ORDER_CHANGE_STATUS_SUCCESS,
			orderService.handleRequest(id, deliveryStatus, token));
	}

	// 4. 주문 취소
	@DeleteMapping("/orders/{id}")
	public CommonResponse cancelOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return CommonResponse.of(SuccessCode.CANCEL_ORDER_SUCCESS);
	}

}
