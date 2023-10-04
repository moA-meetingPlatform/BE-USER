package com.moa.user.common;


import com.moa.user.config.exception.ErrorCode;
import lombok.Getter;


@Getter
public class ErrorResponse {

	private final String message;


	public ErrorResponse(ErrorCode e) {
		this.message = e.getDescription();
	}

}