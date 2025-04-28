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
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CreateOwnerReviewTest {

	@Mock
	private UserRepository userRepository;

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
	class CreateOwnerReview {

		@Test
		@DisplayName("정상 응답")
		void shouldCreateOwnerReview_SuccessResponse() {

		}

		@Test
		@DisplayName("리뷰 답글이 이미 존재하면 ALREADY_RESPONSED_REVIEW 예외 처리")
		void shouldCreateOwnerReview_ALREADY_RESPONSED_REVIEW() {

		}

		@Test
		@DisplayName("토큰에서 추출한 userId와 reviewId에서 추출한 userId가 일치하지 않으면 NO_PERMISSION 예외 처리")
		void shouldCreateOwnerReview_NO_PERMISSION() {

		}

	}
}
