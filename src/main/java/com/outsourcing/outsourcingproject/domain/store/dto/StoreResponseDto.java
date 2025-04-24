package com.outsourcing.outsourcingproject.domain.store.dto;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public class StoreResponseDto {
	private final Long id;
	private final String status;
	private final String name;
	private final LocalTime openTime;
	private final LocalTime closeTime;
	private final int minOrderAmount;
	private final String address;

	public StoreResponseDto(Long id, String status, String name, LocalTime openTime,
		LocalTime closeTime, int minOrderAmount, String address) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
		this.address = address;
	}

	public findStoreResponseDto(Long id, String status, String name) {
		this.id = id;
		this.status = status;
		this.name = name;
	}
}
