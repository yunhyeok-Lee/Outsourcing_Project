package com.outsourcing.outsourcingproject.domain.store.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreRequestDto;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
	@Mock
	private StoreRepository storeRepository;

	@Mock
	private EntityFetcher entityFetcher;

	@InjectMocks
	private StoreService storeService;

	@Test
	void 가게_생성_성공() {
		// given
		Long userId = 1L;
		StoreRequestDto request = new StoreRequestDto("가게", "09:00", "22:00", 10000, "서울");

		User user = new User();
		Store store = Store.builder()
			.name("가게")
			.openTime(LocalTime.of(9, 0))
			.closeTime(LocalTime.of(22, 0))
			.minOrderAmount(10000)
			.address("서울")
			.reviewCounts(0)
			.build();

		when(entityFetcher.getUserOrThrow(userId)).thenReturn(user);
		when(storeRepository.countByUser_IdAndIsDeletedFalse(userId)).thenReturn(0);
		when(storeRepository.save(any(Store.class))).thenReturn(store);

		// when
		var result = storeService.createStore(userId, request);

		// then
		assertThat(result.getName()).isEqualTo("가게");
		assertThat(result.getMinOrderAmount()).isEqualTo(10000);
	}

	@Test
	void 가게_이름으로_조회_성공() {
		// given
		String name = "가게";
		Store store = Store.builder()
			.name(name)
			.openTime(LocalTime.of(9, 0))
			.closeTime(LocalTime.of(22, 0))
			.reviewCounts(5)
			.build();

		when(storeRepository.findByNameAndIsDeletedFalse(name)).thenReturn(List.of(store));

		// when
		var result = storeService.findByName(name);

		// then
		assertThat(result.getStore()).hasSize(1);
		assertThat(result.getStore().get(0).getName()).isEqualTo(name);
	}

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

