package com.moa.user.config.security;


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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity // Spring Security를 활성화 함을 나타내는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final TokenEntryPoint tokenEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


	/**
	 * Spring Security의 필터 체인을 정의하는 메서드
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정을 적용합니다.
			.csrf(CsrfConfigurer::disable) // CSRF 보안을 비활성화합니다. API 서버로 사용하기 때문에 일반적으로 비활성화 합니다.
			.authorizeHttpRequests(
				authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers(org.springframework.web.cors.CorsUtils::isPreFlightRequest)
					.permitAll() // CORS 사전 요청 (preflight)을 위한 설정
					.requestMatchers(
						"/api/v1/user/verify/**",   // 인증 API는 모두 허용
						"/api/v1/user/auth/**", // 인증 API는 모두 허용
						"/api/v1/user/test", // HealthCheck API 허용
						"/error",   // 에러 페이지 허용, 허용하지 않으면 에러 발생 시 (ex. 존재하지 않는 url 요청) 403 Forbidden 응답을 반환합니다.
						"/swagger-ui/**",
						"/swagger-resources/**",
						"/api-docs/**") // 특정 경로 패턴에 대한 요청
					.permitAll() // 해당 경로 패턴에 대한 요청은 모두 허용
					.anyRequest() // 그 외의 모든 요청
					.authenticated() // 인증된 사용자만 접근 허용
			)
			.sessionManagement( // 세션을 사용하지 않습니다. JWT를 사용하기 때문에 상태가 없는 세션 정책을 선택합니다.
				sessionManagement -> sessionManagement
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.exceptionHandling( // 인증 예외 발생 시 처리할 진입점을 설정
				exceptionHandling -> exceptionHandling
					.authenticationEntryPoint(tokenEntryPoint)
					.accessDeniedHandler(jwtAccessDeniedHandler)
			)
			.authenticationProvider(authenticationProvider) // 커스텀 인증 제공자 설정
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

		return http.build();
	}


	/**
	 * CORS 설정을 위한 메서드
	 *
	 * @return CORS 설정
	 */
	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		/*
		 * 모든 Origin에 대해 CORS를 허용합니다.
		 * CorsConfiguration.ALL 대신 특정 Origin을 지정할 수 있습니다.
		 * ex. http://front-domain
		 * */
		configuration.addAllowedOrigin(CorsConfiguration.ALL);

		// 모든 HTTP 메서드에 대해 CORS를 허용합니다.
		configuration.addAllowedMethod("*");

		// 모든 헤더에 대해 CORS를 허용합니다.
		configuration.addAllowedHeader("*");

		// 자격증명과 함께 요청 여부 (Authorization로 사용자 인증 사용 시 true)
		configuration.setAllowCredentials(true);

		// CORS preflight 요청의 결과를 캐시할 시간을 설정합니다.
		configuration.setMaxAge(7200L);

		// 모든 경로에 대해 CORS를 허용합니다.
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
