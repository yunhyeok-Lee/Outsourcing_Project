package com.outsourcing.outsourcingproject.common.enums;

import org.springframework.http.HttpStatus;

public interface BaseCode {
	HttpStatus getStatus();

	String getMessage();
}
