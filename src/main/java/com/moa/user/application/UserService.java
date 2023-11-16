package com.moa.user.application;


import com.moa.user.dto.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;


public interface UserService {

	/**
	 * 사용가능한 id인지 체크
	 */
	void checkCanUseLoginId(String loginId);

	/**
	 * UUID로 사용자 정보 가져오기
	 */
	UserGetProfileDto getUserProfileByUUID(UUID uuid);

	/**
	 * 사용자 비밀번호 변경
	 */
	void updatePasswordLoginUser(UserPwDto userPwDto);

	/**
	 * 사용자 계정 탈퇴
	 */
	Boolean withdrawal(Authentication authentication);

	void modifyCompanyCertification(CompanyCertificationDto companyCertificationDto);

	// 주어진 UUID의 사용자 정보를 수정합니다.
	void modifyUser(UserModifyDto userModifyDto);

	/**
	 * 사용자 검색
	 *
	 * @param nickname 닉네임
	 * @return 사용자 정보 리스트
	 */
	List<UserSearchInfoDto> searchUser(String nickname);

}
