// 패키지 선언: 현재 클래스는 'com.moa.user.config' 패키지에 위치합니다.
package com.moa.user.config;

// 필요한 라이브러리 및 클래스들을 임포트합니다.
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration 어노테이션: 이 클래스는 Spring의 Java 기반 설정 클래스임을 나타냅니다.
@Configuration
// @Slf4j 어노테이션: Lombok 라이브러리를 사용하여 로깅 기능을 제공합니다.
// 이 어노테이션을 통해 'log'라는 이름의 로거 객체가 자동으로 생성됩니다.
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

	// WebMvcConfigurer 인터페이스의 메서드를 오버라이드합니다.
	// 이 메서드는 CORS(Cross-Origin Resource Sharing) 설정을 추가하는 데 사용됩니다.
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 로깅: 메서드가 호출될 때 정보를 로그에 기록합니다.
		log.info("WebMvcConfig.addCorsMappings");

		// 모든 경로('/**')에 대한 CORS 설정을 추가합니다.
		registry.addMapping("/**")
				// 모든 헤더를 허용합니다.
				.allowedHeaders(CorsConfiguration.ALL)
				// 다음의 HTTP 메서드를 허용합니다: GET, POST, PUT, PATCH, DELETE, OPTIONS
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
				// 모든 원본 패턴을 허용합니다.
				.allowedOriginPatterns(CorsConfiguration.ALL)
				// 쿠키 및 자격 증명 정보를 허용합니다.
				.allowCredentials(true);
	}
}

