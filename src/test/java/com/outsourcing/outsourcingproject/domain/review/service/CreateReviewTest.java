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
import com.outsourcing.outsourcingproject.domain.order.entity.DeliveryStatus;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.review.dto.ReviewRequestDto;
import com.outsourcing.outsourcingproject.domain.review.entity.Review;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class CreateReviewTest {

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
	@DisplayName("createReview 메서드는")
	class CreateReview {

		@Test
		@DisplayName("정상 응답")
		void shouldCreateReview_WhenOrderCompletedAndUserMatches() {
			// Given
			Long orderId = 1L;
			String token = "valid.token";
			Long userId = 100L;

			ReviewRequestDto requestDto = new ReviewRequestDto(5.0, "맛있어요", "최고!");

			Order order = mock(Order.class);
			User user = mock(User.class);
			Store store = mock(Store.class);

			// When
			when(reviewRepository.existsByOrder_Id(orderId)).thenReturn(false);
			when(entityFetcher.getOrderOrThrow(orderId)).thenReturn(order);
			when(order.getDeliveryStatus()).thenReturn(DeliveryStatus.COMPLETED);
			when(order.getUser()).thenReturn(user);
			when(user.getId()).thenReturn(userId);
			when(jwtUtil.getUserIdFromToken(token)).thenReturn(userId);
			when(order.getStore()).thenReturn(store);
			when(store.getId()).thenReturn(1L);

			// Then
			reviewService.createReview(orderId, requestDto, token);

			// verify
			verify(reviewRepository, times(1)).save(any(Review.class));
			verify(storeRepository, times(1)).increaseReviewCounts(anyLong());
		}

		@Test
		@DisplayName("이미 리뷰가 존재하면 ALREADY_REVIEW_EXISTS 예외 처리")
		void shouldThrowException_WhenReviewAlreadyExists() {
			// Given
			Long orderId = 1L;
			String token = "valid.token";
			ReviewRequestDto requestDto = new ReviewRequestDto(5.0, "맛있어요", "최고!");

			// When
			when(reviewRepository.existsByOrder_Id(orderId)).thenReturn(true);

			// Then
			assertThatThrownBy(() -> reviewService.createReview(orderId, requestDto, token))
				.isInstanceOf(CustomException.class)
				.extracting("errorCode")
				.isEqualTo(ErrorCode.ALREADY_REVIEW_EXISTS);
		}

		@Test
		@DisplayName("주문이 완료되지 않은 경우 NOT_COMPLETED_ORDER 예외 처리")
		void shouldThrowException_WhenOrderIsNotCompleted() {
			// Given
			Long orderId = 1L;
			String token = "valid.token";

			Order order = mock(Order.class);
			ReviewRequestDto requestDto = new ReviewRequestDto(5.0, "맛있어요", "최고!");

			// When
			when(reviewRepository.existsByOrder_Id(orderId)).thenReturn(false);
			when(entityFetcher.getOrderOrThrow(orderId)).thenReturn(order);
			when(order.getDeliveryStatus()).thenReturn(DeliveryStatus.CONFIRMED); // 완료 아님

			// Then
			assertThatThrownBy(() -> reviewService.createReview(orderId, requestDto, token))
				.isInstanceOf(CustomException.class)
				.extracting("errorCode")
				.isEqualTo(ErrorCode.NOT_COMPLETED_ORDER);
		}

		@Test
		@DisplayName("토큰 사용자와 주문 사용자가 다르면 NO_REVIEW_CREATE_PERMISSION 예외 처리")
		void shouldThrowException_WhenUserIdMismatch() {
			// Given
			Long orderId = 1L;
			String token = "valid.token";

			ReviewRequestDto requestDto = new ReviewRequestDto(5.0, "맛있어요", "최고!");
			Order order = mock(Order.class);
			User user = mock(User.class);

			// When
			when(reviewRepository.existsByOrder_Id(orderId)).thenReturn(false);
			when(entityFetcher.getOrderOrThrow(orderId)).thenReturn(order);
			when(order.getDeliveryStatus()).thenReturn(DeliveryStatus.COMPLETED);
			when(order.getUser()).thenReturn(user);
			when(user.getId()).thenReturn(100L);            // 주문 유저 ID
			when(jwtUtil.getUserIdFromToken(token)).thenReturn(200L); // 토큰 유저 ID

			// Then
			assertThatThrownBy(() -> reviewService.createReview(orderId, requestDto, token))
				.isInstanceOf(CustomException.class)
				.extracting("errorCode")
				.isEqualTo(ErrorCode.NO_REVIEW_CREATE_PERMISSION);
		}
	}
}
