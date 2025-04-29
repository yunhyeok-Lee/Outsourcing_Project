package com.outsourcing.outsourcingproject.domain.menu.dto;

import lombok.Getter;

@Getter
public class MenuRequestDto {

	private final String name;
	private final String content;
	private final int price;

	public MenuRequestDto(String name, String content, int price) {
		this.name = name;
		this.content = content;
		this.price = price;
	}
}
