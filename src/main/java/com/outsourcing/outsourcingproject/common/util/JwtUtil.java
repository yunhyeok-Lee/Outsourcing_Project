package com.outsourcing.outsourcingproject.common.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.user.entity.Authority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	private static final long TOKEN_TIME = 60 * 60 * 1000L; // 1000L = 1000밀리초 = 1초

	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	// 서명에 필요한 Key 객체 생성은 초기화 시 한 번만 수행
	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	// 토큰 생성
	public String createToken(Long userId, String email, Authority authority) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("email", email)
			.claim("authority", authority)
			.setIssuedAt(date)
			.setExpiration(new Date(date.getTime() + TOKEN_TIME))
			.signWith(key, signatureAlgorithm)
			.compact();
	}

	// 토큰에서 유저 정보 + 토큰 정보 추출
	public Claims extractClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (JwtException e) {
			throw new CustomException(ErrorCode.INVALID_SIGNATURE);
		}
	}
}
