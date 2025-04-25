package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum SuccessCode implements BaseCode {
	OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
	SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 되었습니다."),
	LOGIN_SUCCESS(HttpStatus.OK, "로그인 되었습니다."),
	LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 되었습니다."),
	USER_DEACTIVATE_SUCCESS(HttpStatus.OK, "회원탈퇴 되었습니다."),
	USER_UPDATE_SUCCESS(HttpStatus.OK, "회원정보가 수정되었습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	SuccessCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public HttpStatus getStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
