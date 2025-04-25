package com.outsourcing.outsourcingproject.domain.user.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.common.dto.CommonResponse;
import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.domain.user.dto.DeactivationRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginResponseDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserRequestDto;
import com.outsourcing.outsourcingproject.domain.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@PostMapping
	public CommonResponse<LoginResponseDto> signup(@RequestBody @Valid UserRequestDto requestDto,
		HttpServletResponse response) {
		LoginResponseDto dto = userService.signup(requestDto);
		response.setHeader("Authorization", dto.getToken());
		return CommonResponse.of(SuccessCode.SIGNUP_SUCCESS, dto);
	}

	@PostMapping("/login")
	public CommonResponse<Void> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletResponse response) {
		LoginResponseDto dto = userService.login(requestDto);
		response.setHeader("Authorization", dto.getToken());
		return CommonResponse.of(SuccessCode.LOGIN_SUCCESS);
	}

	@PostMapping("/logout")
	public CommonResponse<Void> logout() {
		userService.logout();
		return CommonResponse.of(SuccessCode.LOGOUT_SUCCESS);
	}

	@DeleteMapping
	public CommonResponse<Void> deactivate(@RequestBody @Valid DeactivationRequestDto requestDto) {
		userService.deactivate(requestDto);
		return CommonResponse.of(SuccessCode.USER_DEACTIVATE_SUCCESS);
	}

	@PatchMapping
	public CommonResponse<Void> update(@RequestBody @Valid UpdateRequestDto requestDto) {
		userService.update(requestDto);
		return CommonResponse.of(SuccessCode.USER_UPDATE_SUCCESS);
	}
}
