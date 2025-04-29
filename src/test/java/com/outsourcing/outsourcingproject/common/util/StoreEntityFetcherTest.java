package com.outsourcing.outsourcingproject.common.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
class StoreEntityFetcherTest {

	@Mock
	private StoreRepository storeRepository;

	@InjectMocks
	private EntityFetcher entityFetcher;

	@Test
	void 일치하는_가게id_없으면_CustomException() {
		// given
		Long id = 1L;

		// Stubbing
		when(storeRepository.findById(id)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> entityFetcher.getStoreOrThrow(id))
			.isInstanceOf(CustomException.class);
	}
}
