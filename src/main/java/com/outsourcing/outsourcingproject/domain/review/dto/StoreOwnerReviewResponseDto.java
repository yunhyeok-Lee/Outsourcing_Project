package com.outsourcing.outsourcingproject.domain.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StoreOwnerReviewResponseDto {
	private final Long id;
	private final String title;
	private final String content;
	private final Long parentId;
	private final LocalDateTime createdAt;
	
}
