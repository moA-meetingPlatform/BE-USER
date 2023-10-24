package com.moa.user.config.security;

// 필요한 라이브러리 및 클래스들을 임포트합니다.
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

// 로깅을 위한 Lombok 어노테이션
@Slf4j
// 스프링의 서비스 컴포넌트임을 나타내는 어노테이션
@Service
// 필드 주입을 위한 Lombok 어노테이션
@RequiredArgsConstructor
public class JwtTokenProvider {
    // 환경 변수에 액세스하기 위한 객체
    private final Environment env;

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
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + env.getProperty("JWT.EXPIRATION_TIME",Long.class)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 주어진 JWT 토큰이 유효한지 확인합니다. 토큰의 사용자명과 UserDetails의 사용자명이 일치하고, 토큰이 만료되지 않았는지 확인합니다.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String loginId = getLoginId(token);
        return (loginId.equals(userDetails.getUsername()) && !isTokenExpired(token));
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

    // 환경 변수에서 JWT의 비밀 키를 가져와서 사용 가능한 형태로 변환합니다.
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("JWT.SECRET_KEY"));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
