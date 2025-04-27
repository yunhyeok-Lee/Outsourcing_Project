package com.outsourcing.outsourcingproject.domain.store.dto;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

import com.outsourcing.outsourcingproject.domain.store.entity.StoreStatus;

@Getter
public class StoreResponseDto {
	private final Long id;
	private final StoreStatus status;
	private final String name;
	private final LocalTime openTime;
	private final LocalTime closeTime;
	private final Integer minOrderAmount;
	private final String address;

	@Builder
	public StoreResponseDto(Long id, StoreStatus status, String name, LocalTime openTime,
		LocalTime closeTime, Integer minOrderAmount, String address) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
		this.address = address;
	}
}
