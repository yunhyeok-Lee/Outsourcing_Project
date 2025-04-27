package com.outsourcing.outsourcingproject.domain.review.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class StoreReviewResponseDto {
	private final Long id;
	private final String nickname;
	private final String title;
	private final String content;
	private final Double rating;
	private final LocalDateTime createdAt;
	@Setter
	private StoreOwnerReviewResponseDto ownerReplyDto;
}
