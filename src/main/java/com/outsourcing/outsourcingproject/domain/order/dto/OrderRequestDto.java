package com.outsourcing.outsourcingproject.domain.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

	@NotEmpty(message = "주문할 유저 id를 입력해주세요.")
	private Long userId;

	@NotEmpty(message = "가게 id를 입력해주세요.")
	private Long storeId;

	@NotEmpty(message = "메뉴 id를 입력해주세요.")
	private Long menuId;

}
