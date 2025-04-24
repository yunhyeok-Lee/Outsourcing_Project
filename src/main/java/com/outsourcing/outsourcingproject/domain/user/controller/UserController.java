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
import com.outsourcing.outsourcingproject.domain.user.dto.UpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserRequestDto;
import com.outsourcing.outsourcingproject.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@PostMapping
	public CommonResponse signup(@RequestBody UserRequestDto requestDto) {
		userService.signup(requestDto);
		return CommonResponse.of(SuccessCode.OK);
	}

	@PostMapping("/login")
	public CommonResponse login(@RequestBody LoginRequestDto requestDto) {
		userService.login(requestDto);
		return CommonResponse.of(SuccessCode.OK);
	}

	@PostMapping("/logout")
	public CommonResponse logout() {
		userService.logout();
		return CommonResponse.of(SuccessCode.OK);
	}

	@DeleteMapping
	public CommonResponse deactivate(@RequestBody DeactivationRequestDto requestDto) {
		userService.deactivate(requestDto);
		return CommonResponse.of(SuccessCode.OK);
	}

	@PatchMapping
	public CommonResponse update(@RequestBody UpdateRequestDto requestDto) {
		userService.update(requestDto);
		return CommonResponse.of(SuccessCode.OK);
	}

	// Todo: 유저 정보 조회 API 추가
}
