package com.moa.user.utils;


import com.moa.user.config.exception.CustomException;
import com.moa.user.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
public class AuthUtils {

	public static UUID getCurrentUserUUID(Authentication authentication) {
		UUID uuid;
		if (authentication == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		} else {
			uuid = UUID.fromString(authentication.getName());
		}
		return uuid;
	}


	public static UserDetails getUserDetail(Authentication authentication) {
		UserDetails userDetails;
		if (authentication == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		} else {
			userDetails = (UserDetails) authentication.getPrincipal();
		}
		return userDetails;
	}

}
