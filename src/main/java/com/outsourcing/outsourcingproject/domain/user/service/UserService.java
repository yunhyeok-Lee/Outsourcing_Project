package com.outsourcing.outsourcingproject.domain.user.service;

import org.springframework.stereotype.Service;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.common.util.EntityFetcher;
import com.outsourcing.outsourcingproject.common.util.JwtUtil;
import com.outsourcing.outsourcingproject.common.util.PasswordEncode;
import com.outsourcing.outsourcingproject.domain.user.dto.DeactivationRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.LoginResponseDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UpdateRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserRequestDto;
import com.outsourcing.outsourcingproject.domain.user.dto.UserResponseDto;
import com.outsourcing.outsourcingproject.domain.user.entity.User;
import com.outsourcing.outsourcingproject.domain.user.repository.UserRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final EntityFetcher entityFetcher;
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

		User user = User.builder()
			.email(requestDto.getEmail())
			.password(password)
			.nickname(StringUtils.isBlank(requestDto.getNickname()) ? "익명의 사용자" : requestDto.getNickname())
			.phoneNumber(requestDto.getPhoneNumber())
			.address(requestDto.getAddress())
			.authority(requestDto.getAuthority())
			.build();

		userRepository.save(user);

		String jwtToken = jwtUtil.createToken(user.getId(), user.getAuthority());
		return new LoginResponseDto(jwtToken);
	}

	/*
	로그인 API
	1. 이메일로 탈퇴하지 않은 상태의 유저 조회
	2. 비밀번호 검증
	3. Access Token 발급
	 */
	public LoginResponseDto login(LoginRequestDto requestDto) {
		User user = entityFetcher.getUserOrThrow(requestDto.getEmail(), false);

		if (!passwordEncode.matches(requestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}

		String jwtToken = jwtUtil.createToken(user.getId(), user.getAuthority());

		return new LoginResponseDto(jwtToken);
	}

	/*
	로그아웃 API
	 */
	public void logout() {

	}

	/*
	회원 탈퇴 API
	1. 토큰으로 유저 조회
	2. 비밀번호 검증
	3. 이미 탈퇴한 회원이면 예외 발생
	4. User 테이블의 isDeleted=true로 변경
	 */
	@Transactional
	public void deactivate(DeactivationRequestDto requestDto, String token) {
		Long userId = jwtUtil.getUserIdFromToken(token);
		User user = entityFetcher.getUserOrThrow(userId);

		if (!passwordEncode.matches(requestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}

		if (user.isDeleted()) {
			throw new CustomException(ErrorCode.ALREADY_DEACTIVATED_USER);
		}

		user.updateDeletedStatus();
	}

	/*
	회원 정보 수정 API
	1. 토큰으로 유저 조회
	2. 비밀번호 검증
	3. 유저 정보 수정
	 */
	@Transactional
	public void update(UpdateRequestDto requestDto, String token) {
		Long userId = jwtUtil.getUserIdFromToken(token);
		User user = entityFetcher.getUserOrThrow(userId);

		if (!passwordEncode.matches(requestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}

		String encodedNewPw = passwordEncode.encode(requestDto.getNewPassword());

		user.updateUserInfo(requestDto.getNickname(), encodedNewPw, requestDto.getAddress());
	}

	/*
	유저 정보 조회 API
	 */
	public UserResponseDto findById(Long id) {
		User user = entityFetcher.getUserOrThrow(id);

		UserResponseDto responseDto = UserResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.phoneNumber(user.getPhoneNumber())
			.address(user.getAddress())
			.authority(user.getAuthority())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.isDeleted(user.isDeleted())
			.build();

		return responseDto;
	}
}
