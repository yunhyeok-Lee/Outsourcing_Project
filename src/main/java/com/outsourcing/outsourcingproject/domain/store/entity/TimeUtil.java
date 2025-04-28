package com.outsourcing.outsourcingproject.domain.store.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;

/*
 * string으로 받은 opentime, closetime datetime으로 타입 변형
 * */
public class TimeUtil {
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	public static LocalTime toLocalTime(String timeString) {
		try {
			return LocalTime.parse(timeString, TIME_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new CustomException(ErrorCode.INVALID_TIME_FORMAT);
		}
	}
}

