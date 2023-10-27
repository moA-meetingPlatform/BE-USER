package com.moa.user.application;


import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.dto.UserGetDto;
import com.moa.user.dto.UserPwDto;
import com.moa.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	//	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;


	@Override
	public void checkCanUseLoginId(String loginId) {
		if (userRepository.existsByLoginId(loginId)) throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
	}


	@Override
	public UserGetDto getUserByUUID(UUID uuid) {
		return null;
	}


	@Override
	public void updatePasswordLoginUser(UserPwDto userPwDto, Authentication authentication) {

	}


	@Override
	public Boolean withdrawal(Authentication authentication) {
		return null;
	}

}
