package com.outsourcing.outsourcingproject.domain.menu.controller;

import java.nio.file.attribute.UserPrincipal;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuResponseDto;
import com.outsourcing.outsourcingproject.domain.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping("/menus")
	public ResponseEntity<MenuResponseDto> createMenu(
		@PathVariable Long storesId,
		@RequestBody MenuRequestDto requestDto,
		@RequestAttribute("user") UserPrincipal userPrincipal
	) {
		MenuResponseDto responseDto = menuService.createMenu(storesId, requestDto, userPrincipal);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	//
	// @PatchMapping("/menus/{id}")
	// public ResponseEntity<MenuResponseDto> updateMenu(
	//
	// )
}

