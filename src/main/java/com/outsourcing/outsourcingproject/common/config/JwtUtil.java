package com.outsourcing.outsourcingproject.common.config;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.outsourcing.outsourcingproject.domain.user.entity.Authority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	private static final long TOKEN_TIME = 60 * 60 * 1000L; // 1000L = 1000밀리초 = 1초

	// 서명에 필요한 필드
	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // HS256 - 빠르고 서버가 하나일 때 좋은 알고리즘

	@PostConstruct // 서명에 필요한 리소스 준비 (초기화할 때 한 번만 실행하면 됨)
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey); // Base64로 인코딩되어 있는 secretKey를 디코딩해서 바이트로 저장
		key = Keys.hmacShaKeyFor(bytes); // HS256과 같은 대칭키 알고리즘을 사용할 때 필요한 Key 객체를 생성해주는 메서드
	}

	public String createToken(Long userId, String email, Authority authority) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(userId)) // 유저 식별자 (jwt 내부 데이터는 문자열만 허용)
			.claim("email", email) // 추가로 넣고싶은 사용자 정보
			.claim("authority", authority)
			.setIssuedAt(date)
			.setExpiration(new Date(date.getTime() + TOKEN_TIME))
			.signWith(key, signatureAlgorithm) // 키 + 알고리즘으로 서명
			.compact(); // 최종 문자열로 압축
	}
}
