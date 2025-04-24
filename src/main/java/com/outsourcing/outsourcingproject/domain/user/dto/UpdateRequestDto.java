package com.outsourcing.outsourcingproject.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {
	// Todo: 유효성 검증
	private String nickname;
	private String password;
	private String address;
}
