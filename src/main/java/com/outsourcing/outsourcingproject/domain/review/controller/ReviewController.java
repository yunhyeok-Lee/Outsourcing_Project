package com.outsourcing.outsourcingproject.domain.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.domain.review.dto.ReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewUpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.review.dto.StoreReviewResponseDto;
import com.outsourcing.outsourcingproject.domain.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // @RestControllerAdvice <- 용이하게 사용하기 위함
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
	@GetMapping("/store/{storeId}/review")
	public ResponseEntity<?> getStoreReviews
	(@PathVariable Long storeId,
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
		Pageable pageable) {
		Page<StoreReviewResponseDto> responseDto = reviewService.getStoreReviews(storeId, pageable);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	// 리뷰 수정
	@PatchMapping("/review/{id}")
	public ResponseEntity<?> updateReview(
		@PathVariable Long id,
		@Valid @RequestBody ReviewUpdateRequestDto requestDto) {
		reviewService.updateReview(id, requestDto);
		return new ResponseEntity<>("리뷰를 성공적으로 수정했습니다.", HttpStatus.OK);
	}

	// 리뷰 삭제
	@DeleteMapping("review/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
		return new ResponseEntity<>("리뷰를 성공적으로 삭제했습니다.", HttpStatus.OK);
	}
}
