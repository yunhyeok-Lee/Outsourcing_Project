package com.outsourcing.outsourcingproject.common.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.outsourcing.outsourcingproject.common.enums.ErrorCode;
import com.outsourcing.outsourcingproject.common.exception.CustomException;
import com.outsourcing.outsourcingproject.domain.user.entity.Authority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	private static final long ACCESS_TOKEN_TIME = 30 * 60 * 1000L; // 1000L = 1000밀리초 = 1초
	private static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L;

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

	// access token 생성
	public String createAccessToken(Long userId, Authority authority) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("authority", authority)
			.setIssuedAt(date)
			.setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
			.signWith(key, signatureAlgorithm)
			.compact();
	}

	// refresh token 생성
	public String createRefreshToken(Long userId, Authority authority) {
		Date date = new Date();

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("authority", authority)
			.setIssuedAt(date)
			.setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
			.signWith(key, signatureAlgorithm)
			.compact();
	}

	// access token 에서 데이터 추출
	public Claims extractClaims(String token) throws ExpiredJwtException {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (SignatureException e) {
			throw new CustomException(ErrorCode.INVALID_SIGNATURE);
		}
	}

	// refresh token 에서 데이터 추출
	public Claims extractRefreshClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
			// 토큰 만료 시 RefreshToken 검증
		} catch (JwtException e) {
			throw new CustomException(ErrorCode.INVALID_SIGNATURE);
		}
	}

	// 클라이언트에서 서버로 토큰 전달하면서 자동으로 토큰 앞에 붙어오는 "Bearer " 제거
	public String substringAccessToken(String token) {
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		throw new CustomException(ErrorCode.INVALID_SIGNATURE);
	}

	// 유저 ID 추출
	public Long getUserIdFromToken(String token) {
		String substrToken = substringAccessToken(token);
		return Long.valueOf(extractClaims(substrToken).getSubject());
	}

	// 권한 추출
	public String getAuthorityFromToken(String token) {
		String substrToken = substringAccessToken(token);
		return extractClaims(substrToken).get("authority", String.class);
	}
}
