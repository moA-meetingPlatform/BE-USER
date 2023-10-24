package com.moa.user.config.security;

// 필요한 라이브러리 및 클래스들을 임포트합니다.
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 스프링의 컴포넌트임을 나타내는 어노테이션
@Component
// 필드 주입을 위한 Lombok 어노테이션
@RequiredArgsConstructor
// 로깅을 위한 Lombok 어노테이션
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // JWT 토큰 제공자
    private final JwtTokenProvider jwtTokenProvider;
    // 사용자 정보를 로드하는 서비스
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // HTTP 요청 헤더에서 "Authorization" 값을 가져옵니다.
        final String authHeader = request.getHeader("Authorization");
        String jwt;
        String loginId;

        // 헤더가 "Bearer "로 시작하는 경우에만 처리합니다.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 문자열을 제외하고 JWT 토큰만 추출합니다.
        jwt = authHeader.substring(7);

        // JWT 토큰에서 사용자 ID (UUID)를 추출합니다.
        loginId = jwtTokenProvider.getLoginId(jwt);

        // 사용자 ID가 존재하고 현재 Security Context에 인증 정보가 없는 경우
        if (loginId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 사용자 ID를 기반으로 UserDetails 객체를 로드합니다.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginId);

            // JWT 토큰의 유효성을 검증하고, 사용자의 인증 정보를 생성한 다음, Security Context에 설정합니다.
            if (jwtTokenProvider.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // 현재 요청의 세부 정보를 인증 토큰에 추가합니다.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 인증 토큰을 Security Context에 설정합니다.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 요청을 다음 필터로 전달합니다.
        filterChain.doFilter(request, response);
    }
}
