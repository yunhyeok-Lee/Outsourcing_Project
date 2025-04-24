package com.outsourcing.outsourcingproject.domain.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.domain.review.dto.ReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewUpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	// 리뷰 생성
	@PostMapping("/order/{orderId}/review")
	public ResponseEntity<?> createReview(@PathVariable Long orderId, @Valid @RequestBody ReviewRequestDto requestDto) {
		reviewService.createReview(orderId, requestDto);
		return new ResponseEntity<>("리뷰를 성공적으로 생성하였습니다.", HttpStatus.OK);
	}

	// 가게 리뷰 조회 (최신 순, 별점 별 조회 : 페이징 처리예정)
	// @GetMapping("/store/{storeId}/review")
	// public ResponseEntity<?> getStoreReviews() {
	// 	StoreReviewResponseDto responseDto =
	// 	return new ResponseEntity<>(responseDto , HttpStatus.OK);
	// }

	// 리뷰 수정
	@PatchMapping("/review/{id}")
	public ResponseEntity<?> updateReview(@PathVariable Long id,
		@Valid @RequestBody ReviewUpdateRequestDto requestDto) {
		reviewService.updateReview(id, requestDto);
		return new ResponseEntity<>("리뷰를 성공적으로 수정했습니다.", HttpStatus.OK);
	}

	// 리뷰 삭제
	@PatchMapping("review/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
		return new ResponseEntity<>("리뷰를 성공적으로 삭제했습니다.", HttpStatus.OK);
	}
}
