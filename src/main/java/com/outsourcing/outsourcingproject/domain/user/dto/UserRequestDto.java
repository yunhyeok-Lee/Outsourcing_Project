package com.outsourcing.outsourcingproject.domain.user.dto;

import com.outsourcing.outsourcingproject.domain.user.entity.Authority;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {
	@NotBlank(message = "이메일을 입력해주세요.")
	@Size(max = 50, message = "이메일은 최대 50글자여야 합니다.")
	@Email(message = "이메일 형식이어야 합니다.")
	private final String email;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$", message = "비밀번호는 영문 대/소문자, 숫자를 포함해 8자 이상 20자 이하여야 합니다.")
	private final String password;

	@Size(max = 20, message = "닉네임은 최대 20자여야 합니다.")
	private final String nickname;

	@Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "휴대폰 번호는 010-1234-5678 형식이어야 합니다.")
	private final String phoneNumber;

	@NotBlank(message = "주소를 입력해주세요.")
	@Size(max = 100, message = "주소는 최대 100자여야 합니다.")
	private final String address;

	// Todo: 입력값에 대해 정합성 관리 필요
	private final Authority authority;
}
