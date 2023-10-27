package com.moa.user.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.global.config.exception.ErrorCode;
import com.moa.global.vo.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * 인증된 사용자가 접근 권한이 없는 자원에 접근하려 할 때 호출되는 진입점을 정의합니다.
 * 403 Forbidden 응답을 반환합니다.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;


	/**
	 * 인증된 사용자가 접근 권한이 없는 자원에 접근하려 할 때 호출됩니다.
	 *
	 * @param request               <code>AccessDeniedException</code>
	 * @param response              403 forbidden 응답 반환
	 * @param accessDeniedException that caused the invocation
	 * @throws IOException output 에러 발생 가능
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		log.debug("jwt - jwt AccessDeniedHandler ");

		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpStatus.FORBIDDEN.value());

		// "접근권한 없음"을 나타내는 JSON 메시지를 작성합니다.
		String result = objectMapper.writeValueAsString(ApiResult.ofError(ErrorCode.FORBIDDEN));

		// 응답에 JSON 메시지를 출력합니다.
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

}