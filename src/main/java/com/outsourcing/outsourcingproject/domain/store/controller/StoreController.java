package com.outsourcing.outsourcingproject.domain.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreRequestDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreResponseDto;
import com.outsourcing.outsourcingproject.domain.store.service.StoreService;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

	private final StoreService storeService;

	/*
	 * 가게 등록 api
	 * storeRequestDto로 입력 받아 저장한 뒤
	 * SuccessCode의 status와 message 출력
	 * */
	@PostMapping
	public ResponseEntity<String> createStore(
		//인증된 사용자 정보 가저오기
		User authortyUser,
		@Valid @RequestBody StoreRequestDto storeRequestDto) {
		StoreResponseDto createdStore = storeService.createStore(authortyUser, storeRequestDto);

		return ResponseEntity
			.status(SuccessCode.CREATE_STORE.getStatus())
			.body(SuccessCode.CREATE_STORE.getMessage());
	}
}
