package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum SuccessCode implements BaseCode {

	// User
	OK(HttpStatus.OK, "요청이 성공적으로 처리되었습니다."),
	SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 되었습니다."),
	LOGIN_SUCCESS(HttpStatus.OK, "로그인 되었습니다."),
	LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 되었습니다."),
	USER_DEACTIVATE_SUCCESS(HttpStatus.OK, "회원탈퇴 되었습니다."),
	USER_UPDATE_SUCCESS(HttpStatus.OK, "회원정보가 수정되었습니다."),

	// Store
	CREATE_STORE(HttpStatus.OK, "가게가 생성되었습니다."),
	GET_STORE_LIST(HttpStatus.OK, "가게가 조회되었습니다."),
	DELETE_STORE(HttpStatus.OK, "가게가 폐업 처리되었습니다."),

	// Order
	SENDING_ORDER_SUCCESS(HttpStatus.CREATED, "주문 요청이 완료되었습니다. 주문 수락 전까지 대기 상태입니다."),
	ORDER_CHANGE_STATUS_SUCCESS(HttpStatus.OK, "주문 상태 변경이 완료되었습니다."),
	GET_ORDER_LIST_SUCCESS(HttpStatus.FOUND, "주문 목록을 조회합니다."),
	CANCEL_ORDER_SUCCESS(HttpStatus.OK, "주문이 취소되었습니다.");

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
