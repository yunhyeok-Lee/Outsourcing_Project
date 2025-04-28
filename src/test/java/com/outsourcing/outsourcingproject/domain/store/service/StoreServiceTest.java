package com.outsourcing.outsourcingproject.domain.store.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.menu.repository.MenuRepository;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreRequestDto;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
	@Mock
	private StoreRepository storeRepository;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private EntityFetcher entityFetcher;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private StoreService storeService;

	@Test
	void 가게_최대개수초과이면_CustomException() {
		// given
		Long userId = 1L;
		StoreRequestDto request = new StoreRequestDto("가게", "09:00", "22:00", 10000, "서울");

		when(entityFetcher.getUserOrThrow(userId)).thenReturn(new User());
		when(storeRepository.countByUser_IdAndIsDeletedFalse(userId)).thenReturn(4);

		// when & then
		assertThatThrownBy(() -> storeService.createStore(userId, request))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.STORE_LIMIT_EXCEEDED.getMessage());
	}

	@Test
	void 삭제된_가게_조회시_CustomException() {
		// given
		Long id = 1L;
		Store store = Store.builder()
			.isDeleted(true)
			.build();

		when(entityFetcher.getStoreOrThrow(id)).thenReturn(store);

		// when & then
		assertThatThrownBy(() -> storeService.findStore(id))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.STORE_NOT_FOUND.getMessage());
	}

	@Test
	void 이미_삭제된_가게_삭제시_CustomException() {
		// given
		Long id = 1L;
		Store store = Store.builder()
			.isDeleted(true)
			.build();

		when(entityFetcher.getStoreOrThrow(id)).thenReturn(store);

		// when & then
		assertThatThrownBy(() -> storeService.deleteStore(id))
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.STORE_ALREADY_DELETED.getMessage());
	}
}
