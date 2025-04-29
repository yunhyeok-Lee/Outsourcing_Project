package com.outsourcing.outsourcingproject.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuUpdateRequestDto {

	@NotBlank(message = "수정할 메뉴 이름을 입력하세요.")
	private String name;

	@NotBlank(message = "수정할 메뉴 내용을 입력하세요.")
	private String content;

	@NotNull(message = "수정할 메뉴 가격을 입력하세요.")
	private int price;

	public MenuUpdateRequestDto(String name, String content, int price) {
		this.name = name;
		this.content = content;
		this.price = price;
	}
}
