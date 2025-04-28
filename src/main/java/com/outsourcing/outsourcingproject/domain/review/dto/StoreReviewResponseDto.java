package com.outsourcing.outsourcingproject.domain.review.dto;

import java.time.LocalDateTime;

import com.outsourcing.outsourcingproject.domain.review.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreReviewResponseDto {
	private final Long id;
	private final String nickname;
	private final String title;
	private final String content;
	private final Double rating;
	private final Long parentReviewId;
	private final LocalDateTime createdAt;

	public StoreReviewResponseDto(Review review) {
		this.id = review.getId();
		this.title = review.getTitle();
		this.content = review.getContent();
		this.rating = review.getRating();
		this.createdAt = review.getCreatedAt();
		this.nickname = review.getUser().getNickname();
		this.parentReviewId = (review.getParent() != null) ? review.getParent().getId() : null;
	}
}
