package com.outsourcing.outsourcingproject.domain.review.dto;

import com.outsourcing.outsourcingproject.domain.review.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreReviewResponseDto {
	private final Long id;
	private final String title;
	private final String content;
	private final Double rating;
	private final String nickname;

	public StoreReviewResponseDto(Review review) {
		this.id = review.getId();
		this.nickname = review.getUser().getNickname();
		this.title = review.getTitle();
		this.content = review.getContent();
		this.rating = review.getRating();
	}
}
