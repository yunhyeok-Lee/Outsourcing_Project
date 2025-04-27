package com.outsourcing.outsourcingproject.domain.store.dto;

import java.util.List;

import com.outsourcing.outsourcingproject.domain.menu.dto.MenuResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreAndMenuListResponseDto {
	private final StoreResponseDto store;
	private final List<MenuResponseDto> menu;

}
