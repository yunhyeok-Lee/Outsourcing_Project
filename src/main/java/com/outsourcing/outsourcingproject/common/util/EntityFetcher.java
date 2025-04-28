package com.outsourcing.outsourcingproject.common.util;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.menu.entity.Menu;
import com.outsourcing.outsourcingproject.domain.menu.repository.MenuRepository;
import com.outsourcing.outsourcingproject.domain.order.dto.OrderRequestDto;
import com.outsourcing.outsourcingproject.domain.order.entity.Order;
import com.outsourcing.outsourcingproject.domain.order.entity.OrderEntities;
import com.outsourcing.outsourcingproject.domain.order.repository.OrderRepository;
import com.outsourcing.outsourcingproject.domain.review.entity.Review;
import com.outsourcing.outsourcingproject.domain.review.repository.ReviewRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.entity.StoreStatus;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // 생성자가 한 개인 경우 자동으로 @Autowired 가 붙음
public class EntityFetcher {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final OrderRepository orderRepository;
	private final MenuRepository menuRepository;
	private final ReviewRepository reviewRepository;

	public Order getOrderOrThrow(Long orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
	}

	public Store getStoreOrThrow(Long storeId) {
		return storeRepository.findById(storeId)
			.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
	}

	public User getUserOrThrow(Long userId) {
		return userRepository.findUserById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	public User getUserOrThrow(String email, boolean deleted) {
		return userRepository.findUserByEmailAndIsDeleted(email, deleted)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	public Menu getMenuOrThrow(Long menuId) {
		return menuRepository.findById(menuId)
			.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
	}

	public Review getReviewOrThrow(Long reviewId) {
		return reviewRepository.findById(reviewId)
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
	}

	// 엔티티 한번에 조회할 메서드
	public OrderEntities fetchOrderEntities(Long id, OrderRequestDto dto) {
		User user = getUserOrThrow(id);
		Store store = getStoreOrThrow(dto.getStoreId());
		Menu menu = getMenuOrThrow(dto.getMenuId());

		return new OrderEntities(user, store, menu);
	}

	// 다른 Domain 에서 Store 조회 시 StoreStatus 반영해줄 메서드
	public StoreStatus storeStatus(LocalTime open, LocalTime close, LocalTime now) {
		if (open.isBefore(now) && close.isAfter(now)) {
			return StoreStatus.OPEN;
		}
		return StoreStatus.PREPARING;
	}
}
