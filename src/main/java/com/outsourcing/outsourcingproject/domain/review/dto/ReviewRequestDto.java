package com.outsourcing.outsourcingproject.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {
	@DecimalMin("1.0")
	@DecimalMax("5.0")
	@NotNull
	private final Double rating;
	private final String title;
	private final String content;
}
