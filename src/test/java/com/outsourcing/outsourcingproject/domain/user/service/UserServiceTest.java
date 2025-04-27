package com.outsourcing.outsourcingproject.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.common.util.PasswordEncode;
import com.outsourcing.outsourcingproject.domain.user.dto.DeactivationRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserRequestDto;
import com.outsourcing.outsourcingproject.domain.user.entity.Authority;
import com.outsourcing.outsourcingproject.domain.user.entity.User;
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private PasswordEncode passwordEncode;

	@Mock
	private EntityFetcher entityFetcher;

	@InjectMocks
	private UserService userService;

	@Captor
	ArgumentCaptor<User> userCaptor;

	@Test
	void 이메일이_중복되면_CustomException() {
		// given
		String email = "abc@gmail.com";
		UserRequestDto dto = new UserRequestDto(email, "pw", "nick", "010-0000-0000", "address", Authority.USER);
		User user = new User(email, "password", "nickname", "010-0000-0000", "address", Authority.USER);

		// Stubbing
		when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

		// when & then
		assertThatThrownBy(() -> userService.signup(dto))
			.isInstanceOf(CustomException.class);
	}

	@Test
	void 비밀번호_암호화() {
		// given
		PasswordEncode passwordEncode = new PasswordEncode();
		String password = "password";

		// when
		String encodedPassword = passwordEncode.encode(password);

		// then
		assertThat(passwordEncode.matches(password, encodedPassword)).isEqualTo(true);
	}

	@Test
	void 비밀번호_일치하지_않으면_CustomException() {
		// given
		DeactivationRequestDto dto = new DeactivationRequestDto("rawPassword");
		String token = "token";
		Long userId = 1L;
		User user = new User("email", "password", "nickname", "010-0000-0000", "address", Authority.USER);

		// Stubbing
		when(jwtUtil.getUserIdFromToken(token)).thenReturn(userId);
		when(entityFetcher.getUserOrThrow(userId)).thenReturn(user);
		when(passwordEncode.matches(dto.getPassword(), user.getPassword())).thenReturn(false);

		// when & then
		assertThatThrownBy(() -> userService.deactivate(dto, token))
			.isInstanceOf(CustomException.class);
	}

	@Test
	void nickname이_null이면_기본값_저장() {
		// given
		UserRequestDto dto = new UserRequestDto("email@gmail.com", "pw", null, "010-0000-0000", "address",
			Authority.USER);
		User user = new User("email", "password", "nickname", "010-0000-0000", "address", Authority.USER);

		// Stubbing
		when(userRepository.findUserByEmail("email@gmail.com")).thenReturn(Optional.empty());
		when(passwordEncode.encode(dto.getPassword())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(jwtUtil.createToken(any(), any())).thenReturn("token");

		// when
		userService.signup(dto);

		// then
		verify(userRepository).save(userCaptor.capture());
		User createdUser = userCaptor.getValue();
		assertThat(createdUser.getNickname()).isEqualTo("익명의 사용자");
	}
}
