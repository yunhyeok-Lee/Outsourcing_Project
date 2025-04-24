package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseCode {
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
	ALREADY_DEACTIVATED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 유저입니다."),
	CONFLICT_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),

	//store errorCode
	NO_STORE_PERMISSION(HttpStatus.UNAUTHORIZED, "가게를 등록할 권한이 없습니다."),
	STORE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "생성 가능한 가게 수를 초과하였습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	ErrorCode(HttpStatus httpStatus, String message) {
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
