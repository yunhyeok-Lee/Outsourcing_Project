package com.outsourcing.outsourcingproject.domain.order.dto;

import com.outsourcing.outsourcingproject.domain.user.entity.User;

import lombok.Getter;

@Getter
public class OrderByUserResponseDto {

	private Long userId;
	private String userName;

	public OrderByUserResponseDto(User user) {
		this.userId = user.getId();
		this.userName = user.getNickname();
	}

}
