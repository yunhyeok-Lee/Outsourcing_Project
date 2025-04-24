package com.outsourcing.outsourcingproject.domain.menu.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuResponseDto;
import com.outsourcing.outsourcingproject.domain.menu.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;

	public MenuResponseDto createMenu(Long storesId, MenuRequestDto requestDto) {
		return null;
	}
}
