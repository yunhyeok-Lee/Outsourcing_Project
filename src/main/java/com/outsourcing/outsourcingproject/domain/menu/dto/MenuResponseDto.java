package com.outsourcing.outsourcingproject.domain.menu.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MenuResponseDto {
	private final Long id;
	private final String name;
	private final String content;
	private final int price;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public MenuResponseDto(Long id, String name, String content, int price, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.price = price;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
