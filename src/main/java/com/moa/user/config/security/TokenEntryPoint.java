package com.moa.user.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.global.config.exception.ErrorCode;
import com.moa.global.vo.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * 인증되지 않은 사용자가 보호된 자원에 접근을 시도할 때 호출되는 진입점을 정의합니다.
 * 401 Unauthorized 응답을 반환합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenEntryPoint implements AuthenticationEntryPoint {

	// JSON 변환을 위한 ObjectMapper 객체
	private final ObjectMapper objectMapper;

	//


	/**
	 * 사용자가 인증되지 않은 상태에서 보호된 자원에 접근을 시도할 때 호출됩니다.
	 * 엑세스 토큰이 만료될 경우 이 메서드가 실행됩니다.
	 *
	 * @param request       요청 객체
	 * @param response      401 unauthorized 응답 반환
	 * @param authException 인증 예외
	 * @throws IOException output 에러 발생 가능
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		log.debug("jwt - TokenEntryPoint ");

		// 응답 컨텐트 타입을 JSON으로 설정합니다.
		response.setContentType("application/json; charset=UTF-8");
		// 응답 상태를 "UNAUTHORIZED"으로 설정합니다. (HTTP 상태 코드 401)
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		// "토큰이 유효하지 않음"을 나타내는 JSON 메시지를 작성합니다.
		String result = objectMapper.writeValueAsString(ApiResult.ofError(ErrorCode.UNAUTHORIZED));

		// 응답에 JSON 메시지를 출력합니다.
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

}
