package com.outsourcing.outsourcingproject.domain.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreReviewResponseDto {
	private final Long id;
	private final String title;
	private final String content;
	private final Long rating;
}
