package com.outsourcing.outsourcingproject.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreOwnerReviewResponseDto {
	private final Long id;
	private final String title;
	private final String content;
}
