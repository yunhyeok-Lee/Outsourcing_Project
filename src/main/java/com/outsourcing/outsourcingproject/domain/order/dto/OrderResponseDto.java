package com.outsourcing.outsourcingproject.domain.order.dto;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;

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
	private Long menuCount;

}
