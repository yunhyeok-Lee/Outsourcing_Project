package com.outsourcing.outsourcingproject.domain.order.entity;

// 배달 상태를 eunm으로 받는다
public enum DeliveryStatus {

	/*
	WAITING: 대기 상태,
	CONFIRM: 수락,
	REJECTED: 거절
	 */

	WAITING,
	CONFIRM,
	REJECTED,
	COMPLETED;

}
