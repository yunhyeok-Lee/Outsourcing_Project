package com.outsourcing.outsourcingproject.domain.menu.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.outsourcing.outsourcingproject.common.dto.CommonResponse;
import com.outsourcing.outsourcingproject.common.enums.SuccessCode;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuUpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuController {

	private final JwtUtil jwtUtil;
	private final MenuService menuService;

	// 메뉴 생성
	@PostMapping("/{storeId}/menus")
	public ResponseEntity<CommonResponse<Void>> createMenu(
		@PathVariable Long storeId,
		@Valid
		@RequestBody MenuRequestDto requestDto,
		@RequestHeader("Authorization") String token) {

		Long userId = jwtUtil.getUserIdFromToken(token);
		String authority = jwtUtil.getAuthorityFromToken(token);

		menuService.createMenu(userId, storeId, authority, requestDto);

		return new ResponseEntity<>(CommonResponse.of(SuccessCode.CREATE_MENU), HttpStatus.OK);

	}

	// 메뉴 수정
	@PatchMapping("/menus/{id}")
	public ResponseEntity<CommonResponse<Void>> updateMenu(
		@PathVariable Long id,
		@Valid
		@RequestBody MenuUpdateRequestDto menuUpdateRequestDto,
		@RequestHeader("Authorization") String token) {

		Long userId = jwtUtil.getUserIdFromToken(token);
		String authority = jwtUtil.getAuthorityFromToken(token);

		menuService.updateMenu(userId, authority, id, menuUpdateRequestDto);

		return new ResponseEntity<>(CommonResponse.of(SuccessCode.UPDATE_MENU), HttpStatus.OK);

	}

	// 메뉴 삭제
	@DeleteMapping("/menus/{id}")
	public ResponseEntity<CommonResponse<Void>> deleteMenu(
		@PathVariable Long id,
		@RequestHeader("Authorization") String token) {

		Long userId = jwtUtil.getUserIdFromToken(token);
		String authority = jwtUtil.getAuthorityFromToken(token);

		menuService.deleteMenu(userId, authority, id);

		return new ResponseEntity<>(CommonResponse.of(SuccessCode.DELETE_MENU), HttpStatus.OK);

	}
}

