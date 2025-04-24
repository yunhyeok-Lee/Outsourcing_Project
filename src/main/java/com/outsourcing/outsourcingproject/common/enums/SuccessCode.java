package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum SuccessCode implements BaseCode {
	// Todo: 메세지 세분화
	OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다.");

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
