package com.outsourcing.outsourcingproject.domain.review.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
class DeleteReviewTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private EntityFetcher entityFetcher;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private ReviewService reviewService;

	@Nested
	@DisplayName("리뷰 답글 생성 메서드는")
	class DeleteReview {

		@Test
		@DisplayName("정상 응답 - [일반 사용자 리뷰 삭제] - 리뷰 수 감소")
		void shouldDeleteReview_SuccessUserResponse() {

		}

		@Test
		@DisplayName("정상 응답 - [사장님 리뷰 삭제] - 리뷰 수 유지")
		void shouldDeleteReview_SuccessOwnerResponse() {

		}

		@Test
		@DisplayName("토큰에서 추출한 userId와 reviewId에서 추출한 userId가 일치하지 않으면 NO_PERMISSION 예외 처리")
		void shouldDeleteReview_NO_PERMISSION() {

		}

	}
}
