package com.outsourcing.outsourcingproject.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

	@NotNull(message = "가게 id를 입력해주세요.")
	private final Long storeId;

	@NotNull(message = "메뉴 id를 입력해주세요.")
	private final Long menuId;

}
