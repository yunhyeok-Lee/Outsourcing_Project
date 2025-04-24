package com.outsourcing.outsourcingproject.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewRequestDto {
	private final String title;
	private final String content;

	@DecimalMin("1.0")
	@DecimalMax("5.0")
	private final Double rating;
}
