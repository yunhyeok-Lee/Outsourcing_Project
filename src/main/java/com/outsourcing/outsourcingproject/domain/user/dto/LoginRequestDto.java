package com.outsourcing.outsourcingproject.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
	// Todo: 유효성 검증 추가
	private String email;
	private String password;
}
