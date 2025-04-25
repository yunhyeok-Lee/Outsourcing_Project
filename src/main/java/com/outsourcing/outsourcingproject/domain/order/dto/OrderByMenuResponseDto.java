package com.outsourcing.outsourcingproject.domain.order.dto;

import com.outsourcing.outsourcingproject.domain.menu.entity.Menu;

import lombok.Getter;

@Getter
public class OrderByMenuResponseDto {

	private final Long menuId;
	private final String menuName;

	public OrderByMenuResponseDto(Menu menu) {
		this.menuId = menu.getId();
		this.menuName = menu.getName();
	}
}
