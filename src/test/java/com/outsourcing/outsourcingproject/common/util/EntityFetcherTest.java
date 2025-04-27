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
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class EntityFetcherTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private EntityFetcher entityFetcher;

	@Test
	void 일치하는_유저id_없으면_CustomException() {
		// given
		Long id = 1L;

		// Stubbing
		when(userRepository.findUserById(id)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> entityFetcher.getUserOrThrow(id))
			.isInstanceOf(CustomException.class);
	}

	@Test
	void 일치하는_유저email_없으면_CustomException() {
		// given
		String email = "email@gmail.com";

		// Stubbing
		when(userRepository.findUserByEmailAndIsDeleted(email, false)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> entityFetcher.getUserOrThrow(email, false))
			.isInstanceOf(CustomException.class);
	}
}
