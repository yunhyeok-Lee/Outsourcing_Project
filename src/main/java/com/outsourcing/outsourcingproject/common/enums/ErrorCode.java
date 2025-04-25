package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseCode {

	// USER
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
	ALREADY_DEACTIVATED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 유저입니다."),
	CONFLICT_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),

	// STORE
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게가 존재하지 않습니다."),

	// ORDER
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다."),
	NOT_COMPLETED_ORDER(HttpStatus.BAD_REQUEST, "배달이 완료되지 않았습니다."),

	// Review
	REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "리뷰가 존재하지않습니다."),
	ALREADY_REVIEW_EXISTS(HttpStatus.BAD_REQUEST, "이미 리뷰가 작성된 주문입니다.");

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
