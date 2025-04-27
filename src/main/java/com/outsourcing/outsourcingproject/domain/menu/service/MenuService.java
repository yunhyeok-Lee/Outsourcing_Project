package com.outsourcing.outsourcingproject.domain.menu.service;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuResponseDto;
import com.outsourcing.outsourcingproject.domain.menu.entity.Menu;
import com.outsourcing.outsourcingproject.domain.menu.repository.MenuRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	/*
	 * 메뉴 생성 Service
	 */
	@Transactional
	public MenuResponseDto createMenu(Long userId, String authority, MenuRequestDto menuRequestDto) {
		// 권한 체크
		if (!"OWNER".equals(authority)) {
			throw new CustomException(ErrorCode.NO_AUTHORITY);
		}

		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

		Menu menu = Menu.builder()
			.store(store)
			.name(menuRequestDto.getName())
			.price(menuRequestDto.getPrice())
			.content(menuRequestDto.getContent())
			.menuType()
			.isDeleted(false)
			.build();

		menuRepository.save(menu);

		return new MenuResponseDto(
			menu.getId(),
			menu.getName(),
			menu.getContent(),
			menu.getPrice(),
			menu.getCreatedAt(),
			menu.getUpdatedAt()
		);
	}

	/*
	 * 메뉴 수정 Service
	 */
	@Transactional
	public MenuResponseDto updateMenu(Long userId, String authority, Long id, MenuRequestDto menuRequestDto) {

		if (!"OWNER".equals(authority)) {
			throw new CustomException(ErrorCode.NO_AUTHORITY);
		}
		Menu menu = menuRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

		menu.updateMenu(menuRequestDto);

		return new MenuResponseDto(menu.getId(), menu.getName(), menu.getContent(), menu.getPrice(),
			menu.getCreatedAt(), menu.getUpdatedAt());
	}

	/*
	 * 메뉴 삭제 Service
	 */
	@Transactional
	public void deleteMenu(Long userId, String authority, Long id) {
		if (!"OWNER".equals(authority)) {
			throw new CustomException(ErrorCode.NO_AUTHORITY);
		}

		Menu menu = menuRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

		menu.deleteMenu();
	}

}
