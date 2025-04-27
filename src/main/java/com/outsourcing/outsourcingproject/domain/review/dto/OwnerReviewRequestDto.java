package com.outsourcing.outsourcingproject.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OwnerReviewRequestDto {
	private final String content;
}
