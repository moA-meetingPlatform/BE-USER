package com.moa.user.application;


import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.domain.User;
import com.moa.user.domain.UserScore;
import com.moa.user.dto.*;
import com.moa.user.infrastructure.UserRepository;
import com.moa.user.infrastructure.UserScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	//	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final UserRepository userRepository;
	private final UserScoreRepository userScoreRepository;


	@Override
	public void checkCanUseLoginId(String loginId) {
		if (userRepository.existsByLoginId(loginId)) throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
	}


	@Override
	public UserGetProfileDto getUserProfileByUUID(UUID uuid) {
		// user, userScore 조회
		User user = userRepository.findByUserUuid(uuid).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		UserScore userScore = userScoreRepository.findByUser(user).orElse(null);

		// dto에 user, userScore setting
		UserGetProfileDto dto = modelMapper.map(user, UserGetProfileDto.class);
		dto.setUserScore(userScore);
		return dto;
	}


	@Override
	public void updatePasswordLoginUser(UserPwDto userPwDto) {

	}


	@Override
	public Boolean withdrawal(Authentication authentication) {
		return null;
	}


	/**
	 * 회사 인증 정보 수정
	 *
	 * @param companyCertificationDto
	 */
	@Override
	@Transactional
	public void modifyCompanyCertification(CompanyCertificationDto companyCertificationDto) {
		User user = userRepository.findByUserUuid(companyCertificationDto.getUserUuid()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		user.updateCompanyCertification(companyCertificationDto);
	}


	/**
	 * 사용자 정보 수정
	 *
	 * @param userModifyDto
	 */
	@Override
	@Transactional
	public void modifyUser(UserModifyDto userModifyDto) {
		User user = userRepository.findByUserUuid(userModifyDto.getUserUuid()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		user.updateProfile(userModifyDto);
	}


	@Override
	public List<UserSearchInfoDto> searchUser(String nickname) {
		List<User> userList = userRepository.findTop50ByNicknameContaining(nickname);
		return userList.stream().map(user -> modelMapper.map(user, UserSearchInfoDto.class)).toList();
	}

}
