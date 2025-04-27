package com.outsourcing.outsourcingproject.domain.order.dto;

import java.time.LocalTime;

import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderStatusResponseDto {

	private final Long id;
	private final Long storeId;
	private final DeliveryStatus deliveryStatus;
	private final LocalTime updatedAt;
}
