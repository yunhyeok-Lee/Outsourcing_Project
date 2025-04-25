package com.outsourcing.outsourcingproject.domain.store.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

	// private final StoreService storeService;
	//
	// /*
	//  * 가게 등록 api
	//  * storeRequestDto로 입력 받아 저장한 뒤
	//  * SuccessCode의 status와 message 출력
	//  * */
	// @PostMapping
	// public ResponseEntity<String> createStore(
	// 	//인증된 사용자 정보 가저오기
	// 	//@Auth?
	// 	@Valid @RequestBody StoreRequestDto storeRequestDto) {
	// 	storeService.createStore(storeRequestDto);
	//
	// 	return ResponseEntity
	// 		.status(SuccessCode.OK.getStatus())
	// 		.body(SuccessCode.OK.getMessage());
	// }
}
