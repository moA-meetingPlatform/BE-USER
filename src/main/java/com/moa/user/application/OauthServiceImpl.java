package com.moa.user.application;


import com.moa.company.application.CompanyService;
import com.moa.company.domain.CompanyCategory;
import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.config.security.JwtTokenProvider;
import com.moa.user.domain.OauthLogin;
import com.moa.user.domain.User;
import com.moa.user.dto.LoginResultInfoDto;
import com.moa.user.dto.OauthLoginDto;
import com.moa.user.infrastructure.OauthLoginRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final ModelMapper modelMapper;

	private final OauthLoginRepository oauthLoginRepository;
	private final CompanyService companyService;


	@Override
	public LoginResultInfoDto oauthLogin(OauthLoginDto oauthLoginDto) {

		// oauth id, provider로 user 조회
		OauthLogin oauthLogin = oauthLoginRepository.findByOauthProviderAndOauthUserId(oauthLoginDto.getOauthProvider(), oauthLoginDto.getOauthUserId())
			.orElseThrow(() -> new CustomException(ErrorCode.FAIL_LOGIN));

		User user = oauthLogin.getUser();

		// jwt token 생성 후 LoginInfoDto에 담아서 리턴
		String jwtToken = jwtTokenProvider.generateToken(user);
		LoginResultInfoDto loginResultInfoDto = modelMapper.map(user, LoginResultInfoDto.class);
		loginResultInfoDto.setToken(jwtToken);

		// user의 생년월일로부터 년도 추출
		loginResultInfoDto.setAgeFromBirthdate(user.getBirthdate());

		// user의 회사 id로 회사 카테고리 조회
		CompanyCategory companyCategory;
		if (user.getCompanyId() == null) {
			companyCategory = CompanyCategory.SMALL_COMPANY;
		} else {
			companyCategory = companyService.getCompanyCategoryById(user.getCompanyId());
		}
		loginResultInfoDto.setCompanyCategory(companyCategory);
		return loginResultInfoDto;
	}

}
