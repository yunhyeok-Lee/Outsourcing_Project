package com.outsourcing.outsourcingproject.domain.menu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuResponseDto;
import com.outsourcing.outsourcingproject.domain.menu.dto.MenuUpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.menu.entity.Menu;
import com.outsourcing.outsourcingproject.domain.menu.repository.MenuRepository;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private StoreRepository storeRepository;

	@InjectMocks
	private MenuService menuService;

	@Test
	void 메뉴_생성_성공() {
		// given
		Long userId = 1L;
		Long storeId = 1L;
		String authority = "OWNER";

		MenuRequestDto requestDto = new MenuRequestDto("후라이드치킨", "바삭한 치킨", 18000);

		User owner = new User("email", "password", "nickname", "010-0000-0000", "address", "OWNER");

		Store store = Store.builder()
			.name("치킨집")
			.openTime(LocalTime.of(9, 0))
			.closeTime(LocalTime.of(22, 0))
			.minOrderAmount(10000)
			.address("서울시 강남구")
			.reviewCounts(0)
			.isDeleted(false)
			.user(owner)
			.build();

		// Stubbing
		when(menuRepository.existsByStoreIdAndName(storeId, requestDto.getName())).thenReturn(false);
		when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

		// when
		MenuResponseDto responseDto = menuService.createMenu(userId, storeId, authority, requestDto);

		// then
		assertThat(responseDto.getName()).isEqualTo("후라이드치킨");
		assertThat(responseDto.getContent()).isEqualTo("바삭한 치킨");
		assertThat(responseDto.getPrice()).isEqualTo(18000);
	}

	@Test
	void OWNER_아니면_메뉴_생성_실패() {
		// given
		Long userId = 1L;
		Long storeId = 1L;
		String authority = "USER"; // OWNER가 아님

		MenuRequestDto requestDto = new MenuRequestDto("양념치킨", "매콤달콤", 19000);

		// when & then
		assertThatThrownBy(() -> menuService.createMenu(userId, storeId, authority, requestDto))
			.isInstanceOf(CustomException.class);
	}

	@Test
	void 메뉴_수정_성공() {
		// given
		Long userId = 1L;
		String authority = "OWNER";
		Long menuId = 1L;

		Menu menu = Menu.builder()
			.store(mock(Store.class)) // store는 mock 객체로 대체
			.name("후라이드치킨")
			.content("바삭한 치킨")
			.price(18000)
			.isDeleted(false)
			.build();

		MenuUpdateRequestDto updateRequestDto = new MenuUpdateRequestDto(
			"양념치킨", "매콤달콤", 19000
		);

		// Stubbing
		when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

		// when
		MenuResponseDto updatedMenu = menuService.updateMenu(userId, authority, menuId, updateRequestDto);

		// then
		assertThat(updatedMenu.getName()).isEqualTo("양념치킨");
		assertThat(updatedMenu.getContent()).isEqualTo("매콤달콤");
		assertThat(updatedMenu.getPrice()).isEqualTo(19000);
	}

	@Test
	void 메뉴_수정_권한없으면_CustomException() {
		// given
		Long userId = 1L;
		String authority = "USER"; // OWNER가 아님!
		Long menuId = 1L;

		MenuUpdateRequestDto updateRequestDto = new MenuUpdateRequestDto(
			"양념치킨", "매콤달콤", 19000
		);

		// when & then
		assertThatThrownBy(() -> menuService.updateMenu(userId, authority, menuId, updateRequestDto))
			.isInstanceOf(CustomException.class);
	}

	@Test
	void 수정하려는_메뉴가_없으면_CustomException() {
		// given
		Long userId = 1L;
		String authority = "OWNER"; // 권한은 정상
		Long menuId = 999L; // 없는 메뉴 ID
		MenuUpdateRequestDto updateRequestDto = new MenuUpdateRequestDto(
			"양념치킨", "매콤달콤", 19000
		);

		// Stubbing
		when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> menuService.updateMenu(userId, authority, menuId, updateRequestDto))
			.isInstanceOf(CustomException.class);
	}
}
