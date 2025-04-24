package com.outsourcing.outsourcingproject.domain.review.service;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.domain.review.dto.ReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	// private final OrderRepository orderRepository;

	// 리뷰 생성
	public void createReview(ReviewRequestDto requestDto) {
	}

	// 리뷰 수정
	public void updateReview() {
	}

	// 가게 리뷰 조회
	public void getStoreReviews() {

	}

	// 리뷰 삭제 (soft)
	public void deleteReview() {

	}
}
