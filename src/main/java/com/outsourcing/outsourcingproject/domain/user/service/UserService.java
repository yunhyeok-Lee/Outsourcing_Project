package com.outsourcing.outsourcingproject.domain.user.service;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.user.dto.DeactivationRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserRequestDto;
import com.outsourcing.outsourcingproject.domain.user.entity.User;
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	/*
	회원 가입 API
	1. 이메일 중복 확인
	2. Todo: 비밀번호 암호화
	3. DB에 User 저장
	Todo: save가 잘 처리됐는지 확인하는 절차 어떻게? (컨트롤러에서 무조건 OK 반환하도록 되어 있어서 확인 절차 꼭 필요)
	 */
	public void signup(UserRequestDto requestDto) {
		userRepository.findUserByEmail(requestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.CONFLICT_EMAIL));

		User user = new User(requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname(),
			requestDto.getPhoneNumber(), requestDto.getAddress(), requestDto.getAuthority());

		userRepository.save(user);
	}

	/*
	로그인 API
	1. 이메일로 유저 조회
	2. 탈퇴 여부 검증
	3. Todo: 비밀번호 검증
	4. Todo: jwt 토큰 발급
	 */
	public void login(LoginRequestDto requestDto) {
		User user = userRepository.findUserByEmail(requestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		if (!user.isDeleted()) {
			throw new CustomException(ErrorCode.ALREADY_DEACTIVATED_USER);
		}
	}

	/*
	로그아웃 API
	1. Todo: jwt 토큰 회수
	 */
	public void logout() {
	}

	/*
	회원 탈퇴 API
	1. Todo: jwt 토큰으로 유저 조회
	2. Todo: 비밀번호 검증
	3. Todo: jwt 토큰 회수
	4. Todo: User 테이블의 isDeleted=true로 변경
	 */
	@Transactional
	public void deactivate(DeactivationRequestDto requestDto) {
	}

	/*
	회원 정보 수정 API
	Todo
	 */
	@Transactional
	public void update(UpdateRequestDto requestDto) {

	}
}
