package com.outsourcing.outsourcingproject.domain.menu.controller;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuResponseDto;
import com.outsourcing.outsourcingproject.domain.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	// 메뉴 생성
	@PostMapping("/menu")
	public ResponseEntity<MenuResponseDto> createMenu(
		@PathVariable Long storesId,
		@RequestBody MenuRequestDto requestDto,
		HttpServletRequest httpServletRequest) {

		Long userId = (Long)httpServletRequest.getAttribute("userId");
		String authority = (String)httpServletRequest.getAttribute("authority");

		return ResponseEntity.ok(menuService.createMenu(userId, storesId, authority, requestDto));

	}

	// 메뉴 수정
	@PatchMapping("/menu/{id}")
	public ResponseEntity<MenuResponseDto> updateMenu(
		@PathVariable Long id,
		@RequestBody MenuRequestDto menuRequestDto,
		HttpServletRequest httpServletRequest) {

		Long userId = (Long)httpServletRequest.getAttribute("userId");
		String authority = (String)httpServletRequest.getAttribute("authority");

		return ResponseEntity.ok(menuService.updateMenu(userId, authority, id, menuRequestDto));
	}

	// 메뉴 삭제
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<String> deleteMenu(
		@PathVariable Long id,
		HttpServletRequest httpServletRequest
	) {
		Long userId = (Long)httpServletRequest.getAttribute("userId");
		String authority = (String)httpServletRequest.getAttribute("authority");

		menuService.deleteMenu(userId, authority, id);
		return ResponseEntity.ok("삭제되었습니다.");
	}
}

