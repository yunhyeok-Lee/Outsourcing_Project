package com.outsourcing.outsourcingproject.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/*
 * 가게 생성 요청 dto
 * */
@Setter
@Getter
public class StoreRequestDto {
	@NotBlank(message = "가게 이름을 입력해주세요.")
	private final String name;
	@NotBlank(message = "오픈 시간을 입력해주세요.")
	private final String openTime;
	@NotBlank(message = "마감 시간을 입력해주세요.")
	private final String closeTime;

	private final Integer minOrderAmount;
	@NotBlank(message = "가게 주소를 입력해주세요.")
	private final String address;

	public StoreRequestDto(String name, String openTime, String closeTime, Integer minOrderAmount, String address) {
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
		this.address = address;
	}
}
