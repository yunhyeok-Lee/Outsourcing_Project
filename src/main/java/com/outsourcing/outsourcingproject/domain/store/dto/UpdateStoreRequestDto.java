package com.outsourcing.outsourcingproject.domain.store.dto;

import java.time.LocalTime;

import lombok.Getter;

/*
 * 가게 수정 요청 dto
 * */
@Getter
public class UpdateStoreRequestDto {
	//patch를 통해 입력된 값만 수정
	private final LocalTime openTime;
	private final LocalTime closeTime;
	private final int minOrderAmount;

	public UpdateStoreRequestDto(LocalTime openTime, LocalTime closeTime, int minOrderAmount) {
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
	}
}
