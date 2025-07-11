package com.outsourcing.outsourcingproject.domain.user.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.common.dto.CommonResponse;
import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.domain.user.dto.DeactivationRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginResponseDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserResponseDto;
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
	public CommonResponse<Void> signup(@RequestBody @Valid UserRequestDto requestDto,
		HttpServletResponse response) {
		LoginResponseDto dto = userService.signup(requestDto);
		setToken(response, dto);
		return CommonResponse.of(SuccessCode.SIGNUP_SUCCESS);
	}

	@PostMapping("/login")
	public CommonResponse<Void> login(@RequestBody @Valid LoginRequestDto requestDto,
		HttpServletResponse response) {
		LoginResponseDto dto = userService.login(requestDto);
		setToken(response, dto);
		return CommonResponse.of(SuccessCode.LOGIN_SUCCESS);
	}

	@PostMapping("/logout")
	public CommonResponse<Void> logout() {
		//userService.logout();
		return CommonResponse.of(SuccessCode.LOGOUT_SUCCESS);
	}

	@DeleteMapping
	public CommonResponse<Void> deactivate(@RequestBody @Valid DeactivationRequestDto requestDto,
		@RequestHeader("Authorization") String token) {
		userService.deactivate(requestDto, token);
		return CommonResponse.of(SuccessCode.USER_DEACTIVATE_SUCCESS);
	}

	@PatchMapping
	public CommonResponse<Void> update(@RequestBody @Valid UpdateRequestDto requestDto,
		@RequestHeader("Authorization") String token) {
		userService.update(requestDto, token);
		return CommonResponse.of(SuccessCode.USER_UPDATE_SUCCESS);
	}

	@GetMapping("/{id}")
	public CommonResponse<UserResponseDto> findById(@PathVariable Long id) {
		return CommonResponse.of(SuccessCode.FIND_USER_SUCCESS, userService.findById(id));
	}

	private void setToken(HttpServletResponse response, LoginResponseDto dto) {
		ResponseCookie cookie = ResponseCookie.from("refreshToken", dto.getRefreshToken())
			.httpOnly(true) // JS 접근 방지
			.secure(true)   // HTTPS 환경에서만 전송
			.sameSite("Strict")  // 다른 사이트 요청에서는 이 쿠키를 전송하지 않음
			.path("/")
			.maxAge(Duration.ofMinutes(60 * 24 * 7))
			.build();

		response.setHeader("Authorization", dto.getAccessToken());
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}
}
