package com.outsourcing.outsourcingproject.domain.store.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/*
 * 가게 생성 요청 dto
 * */
@Getter
public class StoreRequestDto {
	@NotBlank
	private String name;
	@NotBlank
	private LocalTime openTime;
	@NotBlank
	private LocalTime closeTime;
	@NotBlank
	private int minOrderAmount;
	@NotBlank
	private String address;

	public StoreRequestDto(String name, LocalTime openTime, LocalTime closeTime, int minOrderAmount, String address) {
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
		this.address = address;
	}
}
