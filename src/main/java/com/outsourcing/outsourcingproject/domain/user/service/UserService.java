package com.outsourcing.outsourcingproject.domain.user.service;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.common.config.JwtUtil;
import com.outsourcing.outsourcingproject.common.config.PasswordEncode;
import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.user.dto.DeactivationRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginResponseDto;
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
	private final PasswordEncode passwordEncode;
	private final JwtUtil jwtUtil;

	/*
	 회원 가입 API
	 1. 이메일 중복 확인
	 2. 비밀번호 암호화
	 3. DB에 User 저장
	 4. 회원가입 시 바로 로그인처리 되도록 토큰 발급
	 */
	public LoginResponseDto signup(UserRequestDto requestDto) {
		if (userRepository.findUserByEmail(requestDto.getEmail()).isPresent()) {
			throw new CustomException(ErrorCode.CONFLICT_EMAIL);
		}

		String password = passwordEncode.encode(requestDto.getPassword());

		User user = new User(requestDto.getEmail(), password, requestDto.getNickname(),
			requestDto.getPhoneNumber(), requestDto.getAddress(), requestDto.getAuthority());

		userRepository.save(user);

		String jwtToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getAuthority());
		return new LoginResponseDto(jwtToken);
	}

	/*
	로그인 API
	1. 이메일로 유저 조회
	2. 탈퇴 여부 검증
	3. 비밀번호 검증
	4. Access Token 발급
	 */
	public LoginResponseDto login(LoginRequestDto requestDto) {
		User user = userRepository.findUserByEmail(requestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		if (user.isDeleted()) {
			throw new CustomException(ErrorCode.ALREADY_DEACTIVATED_USER);
		}

		if (!passwordEncode.matches(requestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}

		String jwtToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getAuthority());
		return new LoginResponseDto(jwtToken);
	}

	/*
	로그아웃 API
	1. Todo: Access Token 만료
	 */
	public void logout() {

	}

	/*
	회원 탈퇴 API
	1. Todo: 토큰으로 유저 조회
	2. Todo: 비밀번호 검증
	3. Todo: User 테이블의 isDeleted=true로 변경
	4. Todo: 토큰 만료
	 */
	@Transactional
	public void deactivate(DeactivationRequestDto requestDto) {
	}

	/*
	회원 정보 수정 API
	1. Todo: 토큰으로 유저 조회
	2. Todo: 유저 정보 수정
	 */
	@Transactional
	public void update(UpdateRequestDto requestDto) {
	}
}
