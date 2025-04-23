package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseCode {
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");

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
