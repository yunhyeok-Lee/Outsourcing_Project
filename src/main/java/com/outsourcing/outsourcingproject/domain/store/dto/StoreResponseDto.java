package com.outsourcing.outsourcingproject.domain.store.dto;

import java.time.LocalTime;

import com.outsourcing.outsourcingproject.domain.store.entity.StoreSatus;

import lombok.Getter;

@Getter
public class StoreResponseDto {
	private final Long id;
	private final StoreSatus status;
	private final String name;
	private final LocalTime openTime;
	private final LocalTime closeTime;
	private final int minOrderAmount;
	private final String address;

	public StoreResponseDto(Long id, StoreSatus status, String name, LocalTime openTime,
		LocalTime closeTime, int minOrderAmount, String address) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
		this.address = address;
	}
}
