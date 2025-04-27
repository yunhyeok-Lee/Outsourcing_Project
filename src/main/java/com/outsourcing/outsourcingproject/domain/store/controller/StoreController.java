package com.outsourcing.outsourcingproject.domain.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreAndMenuListResponseDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreListResponseDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreRequestDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreResponseDto;
import com.outsourcing.outsourcingproject.domain.store.dto.UpdateStoreRequestDto;
import com.outsourcing.outsourcingproject.domain.store.service.StoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

	private final StoreService storeService;
	private final JwtUtil jwtUtil;

	/*
	 * 가게 등록 api
	 * SuccessCode의 status와 message 출력
	 */
	//user 테이블의 id 필요 입력받지 않으니 token에서 id 추출
	@PostMapping
	public ResponseEntity<String> createStore(
		// 인증된 사용자 정보 가저오기
		@RequestHeader("Authorization") String token,
		@Valid @RequestBody StoreRequestDto storeRequestDto) {
		// 토큰에서 id 추출
		Long userId = jwtUtil.getUserIdFromToken(token);

		storeService.createStore(userId, storeRequestDto);

		return ResponseEntity
			.status(SuccessCode.CREATE_STORE.getStatus())
			.body(SuccessCode.CREATE_STORE.getMessage());
	}

	/*
	 * Todo : isDelete 시 조회불가(status.CLOSE)
	 * 가게 조회 api
	 * name을 통해 사용자 조회
	 * 가게명으로 여러건의 가게 조회
	 */
	@GetMapping("/{name}")
	public ResponseEntity<StoreListResponseDto> findByName(@PathVariable String name) {
		StoreListResponseDto storeListResponseDto = storeService.findByName(name);
		return ResponseEntity.ok(storeListResponseDto);
	}

	/* Todo : 가게 단건조회
	 * 가게 단건 조회 api
	 * id을 통해 사용자 조회
	 * 해당 가게의 메뉴 출력
	 */
	@GetMapping("/{id}")
	public ResponseEntity<StoreAndMenuListResponseDto> findStoreAndMenu(@PathVariable Long id) {
		StoreAndMenuListResponseDto findstore = storeService.findStore(id);

		return ResponseEntity.ok(findstore);
	}

	/*
	 * 가게 정보 수정 api
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long id,
		@RequestBody UpdateStoreRequestDto updateStoreRequestDto) {
		StoreResponseDto updatestore = storeService.updateStore(id, updateStoreRequestDto);

		return ResponseEntity.ok(updatestore);
	}

	/*
	 * 가게 삭제 api
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<StoreResponseDto> deleteStore(@PathVariable Long id) {
		StoreResponseDto deletestore = storeService.deleteStore(id);

		return ResponseEntity.ok(deletestore);
	}

}
