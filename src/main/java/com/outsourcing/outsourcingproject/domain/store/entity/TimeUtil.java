package com.outsourcing.outsourcingproject.domain.store.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
 * string으로 받은 opentime, closetime datetime으로 타입 변형
 * */
public class TimeUtil {
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	public static LocalTime toLocalTime(String timeString) {
		return LocalTime.parse(timeString, TIME_FORMATTER);
	}
}

