package com.outsourcing.outsourcingproject.domain.store.dto;

import com.outsourcing.outsourcingproject.domain.store.entity.StoreStatus;

import lombok.Getter;

import com.outsourcing.outsourcingproject.domain.store.entity.StoreStatus;

@Getter
public class FindStoreResponseDto {
	private final Long id;
	private final StoreStatus status;
	private final String name;

	public FindStoreResponseDto(Long id, StoreStatus status, String name) {
		this.id = id;
		this.status = status;
		this.name = name;
	}
}
