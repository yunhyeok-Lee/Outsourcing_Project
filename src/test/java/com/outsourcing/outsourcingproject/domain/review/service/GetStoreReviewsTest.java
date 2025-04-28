package com.outsourcing.outsourcingproject.domain.review.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;
import com.outsourcing.outsourcingproject.domain.review.dto.StoreReviewResponseDto;
import com.outsourcing.outsourcingproject.domain.review.entity.Review;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class GetStoreReviewsTest {

	@Mock
	private UserRepository userRepository;

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
	@DisplayName("가게 리뷰 조회 메서드는")
	class GetStoreReviews {

		@Test
		@DisplayName("정상 응답")
		void shouldGetStoreReviews_SuccessResponse() {
			// Given
			Long storeId = 1L;
			Pageable pageable = PageRequest.of(0, 10);
			User user = mock(User.class);
			List<Long> orderIds = List.of(1L, 2L);
			Review review1 = mock(Review.class);
			Review review2 = mock(Review.class);
			Page<Review> reviewPage = new PageImpl<>(List.of(review1, review2));

			when(review1.getUser()).thenReturn(user);
			when(review2.getUser()).thenReturn(user);
			when(user.getNickname()).thenReturn("길동");
			when(storeRepository.existsById(storeId)).thenReturn(true);
			when(orderRepository.findOrderIdsByStoreId(storeId)).thenReturn(orderIds);
			when(reviewRepository.findReviewsByOrderIdsWithRatingFilter(orderIds, null, null, pageable))
				.thenReturn(reviewPage);

			// When
			Page<StoreReviewResponseDto> result = reviewService.getStoreReviews(storeId, null, null, pageable);

			// Then
			assertThat(result).isNotNull();
			assertThat(result.getContent()).hasSize(2); // 리뷰 2개 반환되는지
			verify(storeRepository).existsById(storeId);
			verify(orderRepository).findOrderIdsByStoreId(storeId);
			verify(reviewRepository).findReviewsByOrderIdsWithRatingFilter(orderIds, null, null, pageable);
		}

		@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
		@Test
		@DisplayName("정상 응답")
		void shouldGetStoreReviews_STORE_NOT_FOUND() {
			// Given
			Long storeId = 1L;
			Pageable pageable = PageRequest.of(0, 10);

			when(storeRepository.existsById(storeId)).thenReturn(false);

			// When & Then
			assertThatThrownBy(() -> reviewService.getStoreReviews(storeId, null, null, pageable))
				.isInstanceOf(CustomException.class)
				.extracting("errorCode")
				.isEqualTo(ErrorCode.STORE_NOT_FOUND);

			// 추가 검증
			verify(orderRepository, never()).findOrderIdsByStoreId(any());
			verify(reviewRepository, never()).findReviewsByOrderIdsWithRatingFilter(any(), any(), any(), any());
		}
	}

}

