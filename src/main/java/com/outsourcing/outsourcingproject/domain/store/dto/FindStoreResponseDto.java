package com.outsourcing.outsourcingproject.domain.store.dto;

import com.outsourcing.outsourcingproject.domain.store.entity.StoreSatus;

import lombok.Getter;

@Getter
public class FindStoreResponseDto {
	private final Long id;
	private final StoreSatus status;
	private final String name;

	public FindStoreResponseDto(Long id, StoreSatus status, String name) {
		this.id = id;
		this.status = status;
		this.name = name;
	}
}
