package com.outsourcing.outsourcingproject.common.exception;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;

public class CustomException extends RuntimeException {
	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}
