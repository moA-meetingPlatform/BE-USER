package com.moa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing // base entity 자동 적용
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class }) // security 기본 설정 중 랜덤유저 생성하는 설정 제외
public class BeUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeUserApplication.class, args);
	}

}
