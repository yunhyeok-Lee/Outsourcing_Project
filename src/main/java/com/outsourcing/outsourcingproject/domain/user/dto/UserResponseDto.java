package com.outsourcing.outsourcingproject.domain.user.dto;

import java.time.LocalDateTime;

import com.outsourcing.outsourcingproject.domain.user.entity.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDto {
	private final Long id;

	private final String email;

	private final String password;

	private final String nickname;

	private final String phoneNumber;

	private final String address;

	private final Authority authority;

	private final LocalDateTime createdAt;

	private final LocalDateTime updatedAt;

	private final boolean isDeleted;
}
