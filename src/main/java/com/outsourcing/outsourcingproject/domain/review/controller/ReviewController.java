package com.outsourcing.outsourcingproject.domain.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.common.dto.CommonResponse;
import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.domain.review.dto.OwnerReviewRequestDto;
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
	@PostMapping("/orders/{orderId}/reviews")
	public ResponseEntity<?> createReview(
		@PathVariable Long orderId,
		@Valid @RequestBody ReviewRequestDto requestDto,
		@RequestHeader("Authorization") String token) {
		reviewService.createReview(orderId, requestDto, token);
		return new ResponseEntity<>(CommonResponse.of(SuccessCode.CREATE_REVIEW), HttpStatus.OK);
	}

	// 사장님 리뷰 생성
	@PostMapping("/{id}/reviews")
	public ResponseEntity<?> createOwnerReview(
		@PathVariable Long id,
		@Valid @RequestBody OwnerReviewRequestDto requestDto,
		@RequestHeader("Authorization") String token) {
		reviewService.createOwnerReview(id, requestDto, token);
		return new ResponseEntity<>(CommonResponse.of(SuccessCode.CREATE_OWNER_REVIEW), HttpStatus.OK);
	}
	//

	// 가게 리뷰 조회 (최신 순, 별점 별 조회 : 페이징 처리예정)
	@GetMapping("/stores/{storeId}/reviews")
	public ResponseEntity<?> getStoreReviews(
		@PathVariable Long storeId,
		@RequestParam(required = false) Integer minRating,
		@RequestParam(required = false) Integer maxRating,
		@PageableDefault(sort = "createdAt")
		Pageable pageable) {
		Page<StoreReviewResponseDto> responseDto = reviewService.getStoreReviews(storeId, minRating, maxRating,
			pageable);
		return new ResponseEntity<>(
			CommonResponse.of(SuccessCode.GET_STORE_REVIEW_LIST, responseDto), HttpStatus.OK);
	}

	// 리뷰 수정
	@PatchMapping("/reviews/{id}")
	public ResponseEntity<?> updateReview(
		@PathVariable Long id,
		@Valid @RequestBody ReviewUpdateRequestDto requestDto,
		@RequestHeader("Authorization") String token) {
		reviewService.updateReview(id, requestDto, token);
		return new ResponseEntity<>(
			CommonResponse.of(SuccessCode.REVIEW_UPDATE_SUCCESS), HttpStatus.OK);
	}

	// 리뷰 삭제
	@DeleteMapping("reviews/{id}")
	public ResponseEntity<?> deleteReview(
		@PathVariable Long id,
		@RequestHeader("Authorization") String token) {
		reviewService.deleteReview(id, token);
		return new ResponseEntity<>(
			CommonResponse.of(SuccessCode.REVIEW_DELETE_SUCCESS), HttpStatus.OK);
	}
}
