package com.outsourcing.outsourcingproject.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

	@NotBlank(message = "사용자 이름을 입력해주세요.")
	private String userName;

	@NotBlank(message = "가게 이름을 입력해주세요.")
	private String storeName;

	@NotBlank(message = "메뉴 이름을 입력해주세요.")
	private String menuName;

	@NotBlank(message = "주문할 메뉴 수량을 입력해주세요.")
	private Long menuCount;

}
