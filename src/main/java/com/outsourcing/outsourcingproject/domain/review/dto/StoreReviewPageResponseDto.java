package com.outsourcing.outsourcingproject.domain.review.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreReviewPageResponseDto {
	private final List<StoreReviewResponseDto> content;
	// private final PageInfoDto page;

	public StoreReviewPageResponseDto(List<StoreReviewResponseDto> content) {
		this.content = content;
	}
}
