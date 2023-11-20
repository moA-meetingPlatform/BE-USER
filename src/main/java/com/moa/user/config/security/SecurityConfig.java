package com.moa.user.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity // Spring Security를 활성화 함을 나타내는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {

	/**
	 * Spring Security의 필터 체인을 정의하는 메서드
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(CsrfConfigurer::disable) // CSRF 보안을 비활성화합니다. API 서버로 사용하기 때문에 일반적으로 비활성화 합니다.
			.authorizeHttpRequests(
				authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest)
					.permitAll() // CORS 사전 요청 (preflight)을 위한 설정
					.anyRequest()
					.permitAll() // 모두 허용
			)
			.sessionManagement( // 세션을 사용하지 않습니다. JWT를 사용하기 때문에 상태가 없는 세션 정책을 선택합니다.
				sessionManagement -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		return http.build();
	}


	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin(CorsConfiguration.ALL);
		//		configuration.addAllowedOrigin("http://localhost:3000");

		configuration.addAllowedMethod(HttpMethod.GET);
		configuration.addAllowedMethod(HttpMethod.POST);
		configuration.addAllowedMethod(HttpMethod.PUT);
		configuration.addAllowedMethod(HttpMethod.DELETE);
		configuration.addAllowedMethod(HttpMethod.OPTIONS);
		configuration.addAllowedMethod(HttpMethod.HEAD);
		configuration.addAllowedMethod(HttpMethod.PATCH);
		configuration.addAllowedMethod(HttpMethod.TRACE);

		configuration.addAllowedHeader("*");
		//        configuration.setAllowCredentials(true);
		configuration.setMaxAge(7200L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("", configuration);
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
