package com.outsourcing.outsourcingproject.domain.order.dto;

import com.outsourcing.outsourcingproject.domain.store.entity.Store;

import lombok.Getter;

@Getter
public class OrderByStoreResponseDto {

	private Long storeId;
	private String storeName;

	public OrderByStoreResponseDto(Store store) {
		this.storeId = store.getId();
		this.storeName = store.getName();
	}
}
