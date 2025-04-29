package com.outsourcing.outsourcingproject.domain.user.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {
	@Test
	void 유효값_전달시_필드_업데이트() {
		// given
		String nickname = "nick";
		String password = "Abc1234!";
		String address = "대한민국 어딘가";
		User user = new User("email", password, nickname, "010-0000-0000", address, "USER");

		// when
		user.updateUserInfo("john", "Def1234!", "대한민국 서울시");

		// then
		assertThat(user.getNickname()).isEqualTo("john");
		assertThat(user.getPassword()).isEqualTo("Def1234!");
		assertThat(user.getAddress()).isEqualTo("대한민국 서울시");
	}

	@Test
	void 공백_전달시_값이_변경되지_않음() {
		// given
		String nickname = "nick";
		String password = "Abc1234!";
		String address = "대한민국 어딘가";
		User user = new User("email", password, nickname, "010-0000-0000", address, "USER");

		// when
		user.updateUserInfo(" ", " ", " ");

		// then
		assertThat(user.getNickname()).isEqualTo(nickname);
		assertThat(user.getPassword()).isEqualTo(password);
		assertThat(user.getAddress()).isEqualTo(address);
	}
}
