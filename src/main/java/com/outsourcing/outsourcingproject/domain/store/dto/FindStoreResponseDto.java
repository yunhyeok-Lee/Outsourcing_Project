package com.outsourcing.outsourcingproject.domain.store.dto;

import com.outsourcing.outsourcingproject.domain.store.entity.StoreStatus;

import lombok.Getter;

@Getter
public class FindStoreResponseDto {
	private final Long id;
	private final StoreStatus status;
	private final String name;
	private final Integer reviewCounts;

	public FindStoreResponseDto(Long id, StoreStatus status, String name, Integer reviewCounts) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.reviewCounts = reviewCounts;
	}
}
