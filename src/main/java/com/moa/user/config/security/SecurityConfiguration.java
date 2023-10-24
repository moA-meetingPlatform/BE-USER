// 패키지 선언
package com.moa.user.config.security;

// 필요한 라이브러리 및 클래스들을 임포트합니다.
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

// Spring의 구성 클래스임을 나타내는 어노테이션
@Configuration
// Spring Security를 활성화 함을 나타내는 어노테이션
@EnableWebSecurity
// 필드 주입을 위한 Lombok 어노테이션
@RequiredArgsConstructor
public class SecurityConfiguration {
    // 의존성 주입된 필드들
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TokenEntryPoint tokenEntryPoint;

    // Spring Security의 필터 체인을 정의하는 메서드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable) // CSRF 보안을 비활성화합니다. API 서버로 사용하기 때문에 일반적으로 비활성화 합니다.
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest)
                                .permitAll() // CORS 사전 요청 (preflight)을 위한 설정
                                .requestMatchers(
                                        "/error",
                                        "/api/v1/login",
                                        "/swagger-ui/**",
                                        "/swagger-resources/**",
                                        "/api-docs/**") // 특정 경로 패턴에 대한 요청
                                .permitAll() // 해당 경로 패턴에 대한 요청은 모두 허용
                                .anyRequest() // 그 외의 모든 요청
                                .authenticated() // 인증된 사용자만 접근 허용
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // 세션을 사용하지 않습니다. JWT를 사용하기 때문에 상태가 없는 세션 정책을 선택합니다.
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(tokenEntryPoint)) // 인증 예외 발생 시 처리할 진입점을 설정
                .authenticationProvider(authenticationProvider) // 커스텀 인증 제공자 설정
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

        return http.build();
    }

    // CORS 설정을 위한 메서드
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return  request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*")); // 모든 출처에서의 요청을 허용
            cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS")); // 허용된 HTTP 메서드 설정
            cors.setAllowedHeaders(List.of("*")); // 모든 헤더를 허용
            return cors;
        };
    }
}
