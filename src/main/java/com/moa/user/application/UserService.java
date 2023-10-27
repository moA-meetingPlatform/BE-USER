package com.moa.user.application;


import com.moa.user.dto.UserGetDto;
import com.moa.user.dto.UserPwDto;
import org.springframework.security.core.Authentication;

import java.util.UUID;


public interface UserService {

	/**
	 * 사용가능한 id인지 체크
	 */
	void checkCanUseLoginId(String loginId);

	/**
	 * UUID로 사용자 정보 가져오기
	 */
	UserGetDto getUserByUUID(UUID uuid);

	/**
	 * 사용자 비밀번호 변경
	 */
	void updatePasswordLoginUser(UserPwDto userPwDto, Authentication authentication);

	/**
	 * 사용자 계정 탈퇴
	 */
	Boolean withdrawal(Authentication authentication);

}
