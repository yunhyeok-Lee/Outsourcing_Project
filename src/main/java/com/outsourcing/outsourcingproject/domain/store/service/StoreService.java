package com.outsourcing.outsourcingproject.domain.store.service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.store.dto.FindStoreResponseDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreListResponseDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreRequestDto;
import com.outsourcing.outsourcingproject.domain.store.dto.StoreResponseDto;
import com.outsourcing.outsourcingproject.domain.store.entity.Store;
import com.outsourcing.outsourcingproject.domain.store.entity.StoreSatus;
import com.outsourcing.outsourcingproject.domain.store.entity.TimeUtil;
import com.outsourcing.outsourcingproject.domain.store.repository.StoreRepository;
import com.outsourcing.outsourcingproject.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
	private final StoreRepository storeRepository;
	// private final MenuRepository menuRepository;
	// private final UserRepository userRepository;

	private static final int MAX_STORE = 3;

	/*
	 * 가게를 생성
	 * 토큰을 통해 인증된 사용자
	 * 권한 설정이 owner인 사용자만 생성가능
	 * 폐업한 가게를 제외하고 최대 3개까지 생성가능
	 * */
	@Transactional
	public StoreResponseDto createStore(
		// 인증된 사용자 권한
		User authortyUser,
		StoreRequestDto storeRequest) {

		// // 유저 권한 owner가 아닐 경우
		// if (authortyUser.getAuthority() != Authority.OWNER) {
		// 	// 권한이 없는 경우 예외 발생
		// 	throw new CustomException(ErrorCode.NO_STORE_PERMISSION);
		// }

		// owner가 등록한 가게 갯수 제한(최대 3개), 삭제 된 가게는 세지 않음
		// isDeleted가 false인 -> 폐업 처리 되지 않은 가게 count
		int storeCount = storeRepository.countByUserAndIsDeletedFalse(authortyUser);

		if (storeCount >= MAX_STORE) {
			// 최대 개수를 초과한 경우 예외 발생
			throw new CustomException(ErrorCode.STORE_LIMIT_EXCEEDED);
		}

		// reauest로 가져온 String 형태의 시간데이터 변형
		LocalTime openTime = TimeUtil.toLocalTime(storeRequest.getOpenTime());
		LocalTime closeTime = TimeUtil.toLocalTime(storeRequest.getCloseTime());

		/*
		 * initialStatus에 status의 defalt 값 지정
		 * newStore에 값 저장
		 * */
		Store newStore = Store.builder()
			.name(storeRequest.getName())
			.openTime(openTime)
			.closeTime(closeTime)
			.minOrderAmount(storeRequest.getMinOrderAmount())
			.address(storeRequest.getAddress())
			.isDeleted(false)
			.user(authortyUser)
			.build();

		/*
		 * newStore에 저장한 값 storeRepository를 통해 db에 저장
		 * */
		Store savedStore = storeRepository.save(newStore);

		return new StoreResponseDto(
			savedStore.getId(),
			StoreSatus.PREPARING,
			savedStore.getName(),
			savedStore.getOpenTime(),
			savedStore.getCloseTime(),
			savedStore.getMinOrderAmount(),
			savedStore.getAddress()
		);

	}

	/*
	 * 가게 이름으로 다건 조회
	 * */
	public StoreListResponseDto findByName(String name) {
		//dto를 배열로 or dto에서 배열로

		List<Store> storeList = storeRepository.findByName(name);

		List<FindStoreResponseDto> responseDtoList = storeList.stream()
			.map(store -> new FindStoreResponseDto(
				store.getId(),
				store.getStatus(),
				store.getName()
			))
			.collect(Collectors.toList());

		return new StoreListResponseDto(responseDtoList);
	}

	/*
	 * id에 해당하는 가게 정보 수정
	 * */
	// public StoreResponseDto updateStore(Long id, UpdateStoreRequestDto requestDto) {
	//
	// 	Store store = storeRepository.findById(id);
	//
	// 	store.updateStore(
	// 		requestDto.getOpenTime(),
	// 		requestDto.getCloseTime(),
	// 		requestDto.getMinOrderAmount()
	// 		);
	//
	// 	return new StoreResponseDto(
	// 		savedStore.getId(),
	// 		StoreSatus.PREPARING,
	// 		savedStore.getName(),
	// 		savedStore.getOpenTime(),
	// 		savedStore.getCloseTime(),
	// 		savedStore.getMinOrderAmount(),
	// 		savedStore.getAddress()
	// 	);
	// }

}
