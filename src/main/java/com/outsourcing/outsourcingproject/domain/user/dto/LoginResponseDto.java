package com.outsourcing.outsourcingproject.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
	private final String accessToken;
	private final String refreshToken;
}
