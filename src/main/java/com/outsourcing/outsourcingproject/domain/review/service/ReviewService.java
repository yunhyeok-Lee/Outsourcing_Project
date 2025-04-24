package com.outsourcing.outsourcingproject.domain.review.service;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewUpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.review.entity.Review;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;

	/* 리뷰 생성
	1. Order 객체 생성(유효성 검사)
	2. 주문 유효성 검사 - 이미 존재하는지
	3. 주문 유효성 검사 - 배달 완료 상태인지
	4. DB에 SAVE
	*/
	public void createReview(Long orderId, ReviewRequestDto requestDto) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
		boolean exists = reviewRepository.existsByOrder_Id(orderId);

		if (exists) {
			throw new CustomException(ErrorCode.ALREADY_REVIEW_EXISTS);
		}

		if (!order.getDeliveryStatus().equals("배달 완료")) {
			throw new CustomException(ErrorCode.NOT_COMPLETED_ORDER);
		}

		Review review = new Review(requestDto.getRating(), requestDto.getTitle(), requestDto.getContent(), order);

		reviewRepository.save(review);
	}

	/* 리뷰 수정
	1.
	2.
	*/
	public void updateReview() {
	}

	/* 가게 리뷰 조회
	1.
	2.
	3.
	4.
	*/
	public void getStoreReviews() {

	}

	/* 리뷰 삭제 (soft)
	1.
	2.
	3.
	4.
	*/
	public void deleteReview() {

	}

	public void updateReview(Long id, ReviewUpdateRequestDto requestDto) {
	}
}
