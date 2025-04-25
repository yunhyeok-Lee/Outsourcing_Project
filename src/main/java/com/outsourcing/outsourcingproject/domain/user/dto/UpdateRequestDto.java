package com.outsourcing.outsourcingproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {
	@Size(max = 20, message = "닉네임은 최대 20자여야 합니다.")
	private final String nickname;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$", message = "비밀번호는 영문 대/소문자, 숫자를 포함해 8자 이상 20자 이하여야 합니다.")
	private final String password;

	@Size(max = 100, message = "주소는 최대 100자여야 합니다.")
	private final String address;
}
