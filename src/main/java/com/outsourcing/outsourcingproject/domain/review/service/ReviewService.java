package com.outsourcing.outsourcingproject.domain.review.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;

	/* 리뷰 생성
	1. 주문 유효성(배달 완료) 검사
	2. DB에 SAVE
	*/
	public void createReview(Long orderId, ReviewRequestDto requestDto) {
		Optional<Order> findOrder = orderRepository.findById(orderId);
		if (findOrder.getDelieveryStatus.equals("배달 완료")) {

		}

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
}
