package com.outsourcing.outsourcingproject.domain.store.dto;

import java.time.LocalTime;

import com.outsourcing.outsourcingproject.domain.store.entity.StoreStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponseDto {
	private final Long id;
	private final StoreStatus status;
	private final String name;
	private final LocalTime openTime;
	private final LocalTime closeTime;
	private final Integer minOrderAmount;
	private final String address;
	private final Integer reviewCounts;

	@Builder
	public StoreResponseDto(Long id, StoreStatus status, String name, LocalTime openTime,
		LocalTime closeTime, Integer minOrderAmount, String address, Integer reviewCounts) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
		this.address = address;
		this.reviewCounts = reviewCounts;
	}
}
