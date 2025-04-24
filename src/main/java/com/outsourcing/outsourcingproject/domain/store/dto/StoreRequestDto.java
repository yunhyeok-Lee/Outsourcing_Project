package com.outsourcing.outsourcingproject.domain.store.dto;

import java.time.LocalTime;

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
	private String name;
	@NotBlank(message = "오픈 시간을 입력해주세요.")
	private LocalTime openTime;
	@NotBlank(message = "마감 시간을 입력해주세요.")
	private LocalTime closeTime;
	@NotBlank(message = "최소주문금액을 입력해주세요.")
	private int minOrderAmount;
	@NotBlank(message = "가게 주소를 입력해주세요.")
	private String address;

	public StoreRequestDto(String name, LocalTime openTime, LocalTime closeTime, int minOrderAmount, String address) {
		this.name = name;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minOrderAmount = minOrderAmount;
		this.address = address;
	}
}
