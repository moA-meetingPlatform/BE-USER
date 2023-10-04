package com.moa.user.config.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {

	NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, "해당 자원이 존재하지 않습니다."),
	DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "이미 존재하는 데이터입니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
	ENTITY_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "db 저장 실패"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

	/*로그인*/
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
	FAIL_LOGIN(HttpStatus.BAD_REQUEST, "로그인 실패"),
	NEED_INTERGRATED_LOGIN(HttpStatus.NOT_FOUND, "통합ID 로그인이 필요합니다"),

	/*유저*/
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.");

	private final HttpStatus status;
	private final String description;


	ErrorCode(HttpStatus status, String description) {
		this.status = status;
		this.description = description;
	}
}