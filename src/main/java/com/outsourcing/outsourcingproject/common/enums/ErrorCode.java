package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseCode {

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
	ALREADY_DEACTIVATED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 유저입니다."),
	CONFLICT_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),
	INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다. 로그인이 필요합니다."),

	// Order
	ORDER_REQUEST_ALREADY_SENT(HttpStatus.BAD_REQUEST, "이미 주문이 접수되었습니다."),
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문 번호는 접수되지 않은 주문 번호입니다."),
	INVALID_ORDER_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 주문 접수 요청입니다."),
	NOT_COMPLETED_ORDER(HttpStatus.BAD_REQUEST, "배달이 완료되지 않았습니다."),

	// Store
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게가 존재하지 않습니다."),
	NO_STORE_PERMISSION(HttpStatus.UNAUTHORIZED, "가게를 등록할 권한이 없습니다."),
	STORE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "생성 가능한 가게 수를 초과하였습니다."),

	// Review
	REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "리뷰가 존재하지않습니다."),
	ALREADY_REVIEW_EXISTS(HttpStatus.BAD_REQUEST, "이미 리뷰가 작성된 주문입니다."),

	// Menu
	UNAUTHORIZED_MENU_ACCESS(HttpStatus.FORBIDDEN, "사장님만 메뉴를 등록할 수 있습니다."),
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴가 존재하지 않습니다.");

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
