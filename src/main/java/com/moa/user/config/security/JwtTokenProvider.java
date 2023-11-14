package com.moa.user.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

	// 환경 변수에서 jwt 설정 가져오기
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${jwt.expiration-time}")
	private Long EXPIRATION_TIME;


	// 주어진 JWT 토큰에서 사용자명 (UUID)을 추출합니다.
	public String getLoginId(String token) {
		return extractClaim(token, Claims::getSubject);
	}


	// JWT 토큰에서 주어진 claim을 추출합니다.
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}


	// 주어진 UserDetails 객체를 사용하여 JWT 토큰을 생성합니다.
	public String generateToken(UserDetails userDetails) {
		return generateToken(Map.of(), userDetails);
	}


	// 주어진 claims와 UserDetails 객체를 사용하여 JWT 토큰을 생성합니다.
	public String generateToken(
		Map<String, Objects> extractClaims,
		UserDetails userDetails
	) {
		return Jwts.builder()
			.setClaims(extractClaims)
			.setSubject(userDetails.getUsername())
			.setIssuer("moa-user")
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}


	// 주어진 JWT 토큰의 만료 시간을 확인합니다.
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}


	// 주어진 JWT 토큰에서 만료 시간을 추출합니다.
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}


	// 주어진 JWT 토큰의 모든 claims를 추출합니다.
	private Claims extractAllClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}


	// 환경변수에서 가져온 JWT의 비밀 키를 사용 가능한 형태로 변환합니다.
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
