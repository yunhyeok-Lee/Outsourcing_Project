package com.outsourcing.outsourcingproject.domain.menu.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.outsourcing.outsourcingproject.domain.menu.repository.MenuRepository;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	// public MenuResponseDto createMenu(Long storesId, MenuRequestDto requestDto, UserPrincipal userPrincipal) {
	// 	// 사장님인지 확인
	// 	if (!userPrincipal.getAuthority().equals("OWNER")) {
	// 		throw new UnauthorizedException("사장님만 메뉴를 등록할 수 있습니다.");
	// 	}
	//
	// 	// 가게 확인
	// 	Store sotre = storeRepository.findById(storesId)
	// 		.orElseThrow(() -> new NotFoundException("가게가 존재하지 않습니다."));
	//
	// 	// 가게 주인 확인
	// 	if (!store.getOwnerId().equals(userPrincipal.getUserId())) {
	// 		throw new ForbiddenException("본인 가게에만 등록 가능합니다.");
	// 	}
	//
	// 	// 메뉴 생성 및 저장
	// 	Menu menu = new Menu(requestDto.getName(), requestDto.getContent(), requestDto.getPrice(), store);
	// 	menuRepository.save(menu);
	//
	// 	return new MenuResponseDto(menu);
	// }
}
