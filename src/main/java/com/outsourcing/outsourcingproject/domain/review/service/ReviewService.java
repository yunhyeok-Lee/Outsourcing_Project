package com.outsourcing.outsourcingproject.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
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

	/* 가게 리뷰 조회
	1.
	2.
	3.
	4.
	*/
	public void getStoreReviews() {

	}

	/* 리뷰 수정
	0. Transactional 처리
	1. 리뷰 유효성 검사 - 이미 존재하는지 확인
	2. 객체 생성
	3. requestDto 각 필드에 값이 존재하는지 유효성 검사와 값 변경 진행 (PATCH 방식이라 바뀌지 않은 값이 있을것)
	*/
	@Transactional
	public void updateReview(Long id, ReviewUpdateRequestDto requestDto) {
		boolean exists = reviewRepository.existsById(id);
		if (!exists) {
			throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
		}

		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		if (requestDto.getTitle() != null) {
			review.UpdateTitle(requestDto.getTitle());
		}

		if (requestDto.getContent() != null) {
			review.UpdateContent(requestDto.getTitle());
		}

		if (requestDto.getRating() != null) {
			review.UpdateRaing(requestDto.getRating());
		}

	}

	/* 리뷰 삭제 (soft)
	0. Entity에 SQLDelete 어노테이션 설정
	1. 리뷰 유효성 검사 - 이미 존재하는지 확인 (Boolean 방식 채용했기에 객체 생성해서 확인하는 것보다 상대적으로 메모리 ↓)
	2. 객체 생성 (유효성 검사에서 걸리면 객체 생성 안 해도 되니까 유효성 검사 이후에 진행하는 것)
	3. DELETE 진행 ( SQLDelete에서 대신 설정해둔 쿼리문이 실행됨 ) -> SOFT DELETE
	*/
	@Transactional
	public void deleteReview(Long id) {

		boolean exists = reviewRepository.existsById(id);
		if (!exists) {
			throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
		}

		Review savedReview = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		reviewRepository.delete(savedReview);
	}

}
