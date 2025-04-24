package com.outsourcing.outsourcingproject.domain.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewRequestDto {

	private final String title;

	private final String content;
	
	private final Long rating;
}
