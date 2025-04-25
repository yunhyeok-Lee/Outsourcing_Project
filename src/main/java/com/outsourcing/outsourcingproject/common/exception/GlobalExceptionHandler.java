package com.outsourcing.outsourcingproject.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.outsourcing.outsourcingproject.common.dto.CommonResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CommonResponse<Object>> handleCustomException(CustomException customException) {
		return ResponseEntity
			.status(customException.getErrorCode().getStatus())
			.body(CommonResponse.of(customException.getErrorCode()));
	}

}
