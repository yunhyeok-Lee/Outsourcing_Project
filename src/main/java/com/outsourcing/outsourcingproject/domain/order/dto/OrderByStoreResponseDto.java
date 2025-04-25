package com.outsourcing.outsourcingproject.domain.order.dto;

import com.outsourcing.outsourcingproject.domain.store.entity.Store;

import lombok.Getter;

@Getter
public class OrderByStoreResponseDto {

	private final Long storeId;
	private final String storeName;

	public OrderByStoreResponseDto(Store store) {
		this.storeId = store.getId();
		this.storeName = store.getName();
	}
}
