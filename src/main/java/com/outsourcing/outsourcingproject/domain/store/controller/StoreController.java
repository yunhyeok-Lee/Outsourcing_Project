package com.outsourcing.outsourcingproject.domain.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreRequestDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreResponseDto;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.store.service.StoreService;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

	private final StoreService storeService;
	private final StoreRepository storeRepository;

	/*
	 * 가게 등록 api
	 * SuccessCode의 status와 message 출력
	 * */
	@PostMapping
	public ResponseEntity<String> createStore(
		@SessionAttribute(name = "user_id") Long id,
		//인증된 사용자 정보 가저오기
		User authortyUser,
		@Valid @RequestBody StoreRequestDto storeRequestDto) {
		StoreResponseDto createdStore = storeService.createStore(authortyUser, storeRequestDto);

		return ResponseEntity
			.status(SuccessCode.CREATE_STORE.getStatus())
			.body(SuccessCode.CREATE_STORE.getMessage());
	}

	/*
	 * 가게 조회 api
	 * name을 통해 사용자 조회
	 * 가게명으로 여러건의 가게 조회
	 */
	// @PostMapping("/{name}")
	// public ResponseEntity<List<FindStoreResponseDto>> findByName(@PathVariable String name) {
	// 	List<FindStoreResponseDto> findStoreResponseDto = storeService.findByName(name);
	// 	return ResponseEntity.ok(findStoreResponseDto);
	// }

}
