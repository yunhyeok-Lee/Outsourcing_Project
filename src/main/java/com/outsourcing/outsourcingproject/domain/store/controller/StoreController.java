package com.outsourcing.outsourcingproject.domain.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreListResponseDto;
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


	 /* // @SessionAttribute(name = "user_id") Long id, -> 세션에 가까움
		// token -> 회사의 라이브러리 활용, 발급 후 키를 활용해 사용
		// 세션스토리지, 쿠키 -> 헤더
		// 헤더에 토큰정보
		// @RequestHeader // 토큰을 받을 Dto 필요
		//
		// 헤더의 값 받아올 수 있음
		// 클레임 -> role and status를 알 수 있는 값 형태(user_id)
		// JwtParser -> 검색 필요 / 복호화 할 수 있도록 -> 클레임 값 획득
		// -> 중복코드 많아짐 -> aop 활용
		// 필터 사용 가능 /.controller에서 -> 복호화 중복코드 많다면 -> 필터 등의 앞 단에서 복호화
	 * */

	/*
	 * 가게 등록 api
	 * SuccessCode의 status와 message 출력
	 */
	@PostMapping
	public ResponseEntity<String> createStore(
		// 인증된 사용자 정보 가저오기
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
	@PostMapping("/{name}")
	public ResponseEntity<StoreListResponseDto> findByName(@PathVariable String name) {
		StoreListResponseDto storeListResponseDto = storeService.findByName(name);
		return ResponseEntity.ok(storeListResponseDto);
	}

}
