package com.outsourcing.outsourcingproject.domain.user.dto;

import com.outsourcing.outsourcingproject.domain.user.entity.Authority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {
	// Todo: 유효성 검증 추가
	private String email;
	private String password;
	private String nickname;
	private String phoneNumber;
	private String address;
	private Authority authority;
}
