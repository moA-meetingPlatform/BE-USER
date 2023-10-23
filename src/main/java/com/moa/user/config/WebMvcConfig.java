package com.moa.user.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		log.info("WebMvcConfig.addCorsMappings");
		registry.addMapping("/**")
			.allowedMethods(CorsConfiguration.ALL)
			.allowedHeaders(CorsConfiguration.ALL)
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedOriginPatterns(CorsConfiguration.ALL)
			.allowCredentials(true);
	}


}

