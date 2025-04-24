package com.outsourcing.outsourcingproject.domain.order.dto;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponseDto {

	private Long orderId;
	private DeliveryStatus deliveryStatus;
	private Long userId;
	private Long storeId;
	private Long menuId;

	public OrderResponseDto(Order order) {
		this.orderId = order.getOrderId();
		this.deliveryStatus = order.getDeliveryStatus();
		this.userId = order.getUser().getId();
		this.storeId = order.getStore().getId();
		this.menuId = order.getMenu().getId();
	}
}
