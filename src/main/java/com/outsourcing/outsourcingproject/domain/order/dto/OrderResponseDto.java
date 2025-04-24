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
	private String userName;
	private String storeName;
	private String menuName;

	public OrderResponseDto(Order order) {
		this.orderId = order.getOrderId();
		this.deliveryStatus = order.getDeliveryStatus();
		this.userName = order.getUser().getNickname();
		this.storeName = order.getStore().getName();
		this.menuName = order.getMenu().getName();
	}

}
