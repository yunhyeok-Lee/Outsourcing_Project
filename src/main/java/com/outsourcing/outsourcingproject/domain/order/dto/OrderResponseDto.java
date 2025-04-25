package com.outsourcing.outsourcingproject.domain.order.dto;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponseDto {

	private final Long orderId;
	private final Long userId;
	private final Long storeId;
	private final Long menuId;
	private final DeliveryStatus deliveryStatus;

	public OrderResponseDto(Order order) {
		this.orderId = order.getId();
		this.deliveryStatus = order.getDeliveryStatus();
		this.userId = order.getUser().getId();
		this.storeId = order.getStore().getId();
		this.menuId = order.getMenu().getId();
		this.deliveryStatus = order.getDeliveryStatus();
	}
}
