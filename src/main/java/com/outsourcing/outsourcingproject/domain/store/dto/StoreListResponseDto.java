package com.outsourcing.outsourcingproject.domain.store.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreListResponseDto {
	private List<FindStoreResponseDto> store;

}
