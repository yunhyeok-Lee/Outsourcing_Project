package com.outsourcing.outsourcingproject.domain.order.entity;

// 배달 상태를 eunm으로 받는다
public enum DeliveryStatus {

	/*
	WAITING: 대기 상태,
	CONFIRMED: 수락,
	REJECTED: 거절,
	COMPLETED: 배달 완료
	 */

	WAITING,
	CONFIRMED,
	REJECTED,
	COMPLETED;

}
