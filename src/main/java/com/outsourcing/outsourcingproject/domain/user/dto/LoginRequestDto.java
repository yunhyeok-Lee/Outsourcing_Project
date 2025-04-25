package com.outsourcing.outsourcingproject.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
	@NotBlank(message = "이메일을 입력해주세요.")
	@Size(max = 50, message = "이메일은 최대 50글자여야 합니다.")
	@Email(message = "이메일 형식이어야 합니다.")
	private String email;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$", message = "비밀번호는 영문 대/소문자, 숫자를 포함해 8자 이상 20자 이하여야 합니다.")
	private String password;
}
