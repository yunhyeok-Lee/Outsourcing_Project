package com.outsourcing.outsourcingproject.domain.menu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.outsourcing.outsourcingproject.domain.menu.dto.MenuRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuResponseDto;
import com.outsourcing.outsourcingproject.domain.menu.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping("/menus")
	public ResponseEntity<MenuResponseDto> creatMenu(
		@PathVariable Long storesId,
		@RequestBody MenuRequestDto requestDto
	) {
		MenuResponseDto responseDto = menuService.createMenu(storesId, requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@PatchMapping("/menus/{id}")
	public ResponseEntity<MenuResponseDto> updateMenu(
	//
	// )
}

