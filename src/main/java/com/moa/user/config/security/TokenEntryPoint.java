// 패키지 선언: 현재 클래스는 'com.moa.user.config.security' 패키지에 위치합니다.
package com.moa.user.config.security;

// 필요한 라이브러리 및 클래스들을 임포트합니다.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.global.vo.ResponseOut;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로깅을 위한 어노테이션
@Slf4j
// 스프링 빈으로 등록하기 위한 어노테이션
@Component
// 생성자 주입을 위한 Lombok 어노테이션
@RequiredArgsConstructor
// TokenEntryPoint 클래스는 인증되지 않은 사용자가 보호된 자원에 접근을 시도할 때 호출되는 진입점을 정의합니다.
public class TokenEntryPoint implements AuthenticationEntryPoint {
    // JSON 변환을 위한 ObjectMapper 객체
    private final ObjectMapper objectMapper;

    // 사용자가 인증되지 않은 상태에서 보호된 자원에 접근을 시도할 때 호출되는 메서드
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 주석: 엑세스 토큰이 만료될 경우 이 메서드가 실행됩니다.

        // 응답 상태를 "FORBIDDEN"으로 설정합니다. (HTTP 상태 코드 403)
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // 응답 컨텐트 타입을 JSON으로 설정합니다.
        response.setContentType("application/json; charset=UTF-8");
        // 응답 본문에 "토큰이 유효하지 않음"을 나타내는 JSON 메시지를 작성합니다.
        response.getWriter().print(objectMapper.writeValueAsString(ResponseOut.tokenInvalid()));
    }
}
