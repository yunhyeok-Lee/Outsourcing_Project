package com.outsourcing.outsourcingproject.domain.review.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.review.dto.OwnerReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.entity.Review;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;
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

			//Given
			Long id = 1L;
			String token = "valid.token";
			Long userId = 100L;
			String nickname = "길동";

			Review review = mock(Review.class);
			Store reviewStore = mock(Store.class);
			User ownerUser = mock(User.class);
			Order order = mock(Order.class);

			OwnerReviewRequestDto requestDto = new OwnerReviewRequestDto("리뷰 감사합니다!");

			// When
			when(entityFetcher.getReviewOrThrow(id)).thenReturn(review);
			when(reviewRepository.existsByParent(review)).thenReturn(false);
			when(review.getStore()).thenReturn(reviewStore);
			when(reviewStore.getUser()).thenReturn(ownerUser);
			when(ownerUser.getId()).thenReturn(userId);
			when(review.getUser()).thenReturn(ownerUser);
			when(review.getUser().getNickname()).thenReturn(nickname);
			when(review.getOrder()).thenReturn(order);

			when(jwtUtil.getUserIdFromToken(token)).thenReturn(userId);

			// Then
			reviewService.createOwnerReview(id, requestDto, token);
			verify(reviewRepository, times(1)).save(any(Review.class));

		}

		@Test
		@DisplayName("리뷰 답글이 이미 존재하면 ALREADY_RESPONSED_REVIEW 예외 처리")
		void shouldCreateOwnerReview_ALREADY_RESPONSED_REVIEW() {
			//Given
			Long id = 1L;
			String token = "valid.token";

			Review review = mock(Review.class);
			OwnerReviewRequestDto requestDto = new OwnerReviewRequestDto("리뷰 감사합니다!");

			// When
			when(entityFetcher.getReviewOrThrow(id)).thenReturn(review);
			when(reviewRepository.existsByParent(review)).thenReturn(true);

			// Then
			assertThatThrownBy(() -> reviewService.createOwnerReview(id, requestDto, token))
				.isInstanceOf(CustomException.class)
				.extracting("ErrorCode")
				.isEqualTo(ErrorCode.ALREADY_RESPONSED_REVIEW);
		}

		@Test
		@DisplayName("토큰에서 추출한 userId와 reviewId에서 추출한 userId가 일치하지 않으면 NO_PERMISSION 예외 처리")
		void shouldCreateOwnerReview_NO_PERMISSION() {
			//Given
			Long id = 1L;
			String token = "valid.token";
			Long userId = 100L;

			Review review = mock(Review.class);
			Store reviewStore = mock(Store.class);
			User ownerUser = mock(User.class);
			OwnerReviewRequestDto requestDto = new OwnerReviewRequestDto("리뷰 감사합니다!");

			// When
			when(entityFetcher.getReviewOrThrow(id)).thenReturn(review);
			when(reviewRepository.existsByParent(review)).thenReturn(false);
			when(review.getStore()).thenReturn(reviewStore);
			when(reviewStore.getUser()).thenReturn(ownerUser);
			when(ownerUser.getId()).thenReturn(userId);
			when(jwtUtil.getUserIdFromToken(token)).thenReturn(200L);

			// Then
			assertThatThrownBy(() -> reviewService.createOwnerReview(id, requestDto, token))
				.isInstanceOf(CustomException.class)
				.extracting("ErrorCode")
				.isEqualTo(ErrorCode.NO_OWNER_REVIEW_PERMISSION);

		}

	}
}
