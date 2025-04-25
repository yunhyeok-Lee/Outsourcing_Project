package com.outsourcing.outsourcingproject.domain.review.dto;

import com.outsourcing.outsourcingproject.domain.review.entity.Review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreReviewResponseDto {
	private final Long id;
	private final String title;
	private final String content;
	private final Double rating;

	public StoreReviewResponseDto(Review review) {
		this.id = review.getId();
		this.title = review.getTitle();
		this.content = review.getContent();
		this.rating = review.getRating();
	}
}
