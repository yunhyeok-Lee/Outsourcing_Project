package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseCode {

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
	ALREADY_DEACTIVATED_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 유저입니다."),
	CONFLICT_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),
	INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다. 로그인이 필요합니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력한 값의 형식이 잘못되었습니다."),

	// Order
	ORDER_REQUEST_ALREADY_SENT(HttpStatus.BAD_REQUEST, "이미 주문이 접수되었습니다."),
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "접수되지 않은 주문 번호입니다."),
	INVALID_ORDER_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 주문 접수 요청입니다."),
	NOT_COMPLETED_ORDER(HttpStatus.BAD_REQUEST, "배달이 완료되지 않았습니다."),
	STORE_NOT_OPEN(HttpStatus.NOT_ACCEPTABLE, "가게 오픈 시간에 주문을 접수해주세요."),

	// Store
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "가게가 존재하지 않습니다."),
	NO_STORE_PERMISSION(HttpStatus.UNAUTHORIZED, "가게를 등록할 권한이 없습니다."),
	STORE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "생성 가능한 가게 수를 초과하였습니다."),
	STORE_ALREADY_DELETED(HttpStatus.NOT_FOUND, "가게가 이미 폐업처리 되었습니다."),
	INVALID_TIME_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 시간 형식입니다."),

	// Review
	REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "리뷰가 존재하지않습니다."),
	ALREADY_REVIEW_EXISTS(HttpStatus.BAD_REQUEST, "이미 리뷰가 작성된 주문입니다."),
	ALREADY_RESPONSED_REVIEW(HttpStatus.BAD_REQUEST, "이미 답글을 작성한 리뷰입니다."),
	NO_REVIEW_CREATE_PERMISSION(HttpStatus.UNAUTHORIZED, "해당 리뷰를 작성할 권한이 없습니다."),
	NO_REVIEW_UPDATE_PERMISSION(HttpStatus.UNAUTHORIZED, "해당 리뷰를 수정할 권한이 없습니다."),
	NO_REVIEW_DELETE_PERMISSION(HttpStatus.UNAUTHORIZED, "해당 리뷰를 삭제할 권한이 없습니다."),
	NO_OWNER_REVIEW_PERMISSION(HttpStatus.UNAUTHORIZED, "사장님 리뷰를 작성할 권한이 없습니다."),

	// Menu
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴가 존재하지 않습니다."),
	UNAUTHORIZED_MENU_ACCESS(HttpStatus.FORBIDDEN, "사장님만 메뉴를 등록할 수 있습니다."),
	NO_AUTHORITY(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
	DUPLICATE_MENU_NAME(HttpStatus.BAD_REQUEST, "이미 추가 된 메뉴 이름입니다.");

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
