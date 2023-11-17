package com.moa.user.presentation;


import com.moa.company.dto.CompanySimpleInfoDto;
import com.moa.global.vo.ApiResult;
import com.moa.user.application.CertificationService;
import com.moa.user.config.security.JwtTokenProvider;
import com.moa.user.dto.ConfirmCompanyEmailDto;
import com.moa.user.vo.request.SendEmailConfirmRequest;
import com.moa.user.vo.request.SendEmailRequest;
import com.moa.user.vo.response.CompanyVerifyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "Certification Controller", description = "외부 인증을 사용하는 API")
@RestController
@RequestMapping("/api/v1/user/verify")
@Slf4j
@RequiredArgsConstructor
public class CertificationController {

	private final ModelMapper modelMapper;
	private final CertificationService certificationService;
	private final JwtTokenProvider jwtTokenProvider;


	@Operation(summary = "회사 이메일 인증 요청", description = "이메일 인증 요청 -> 인증 코드를 포함한 이메일을 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "이메일 전송 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
		@ApiResponse(responseCode = "404", description = "저장된 회사 이메일이 아님", content = @Content(schema = @Schema(implementation = ApiResult.class)))
	})
	@PostMapping("/email-send")
	public ResponseEntity<ApiResult<Void>> sendCertificationEmail(@RequestBody SendEmailRequest request) {
		certificationService.sendEmail(request.getCompanyEmail());
		return ResponseEntity.ok(ApiResult.ofSuccess(null));
	}


	@Operation(summary = "이메일 인증 번호 확인 (로그인한 경우 회사 인증 정보 update)", description = "이메일 인증번호 확인, 로그인한 경우 회사 인증 정보 update")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "이메일 인증 실패", content = @Content(schema = @Schema(implementation = ApiResult.class))),
	})
	@PatchMapping("/email-confirm")
	public ResponseEntity<ApiResult<CompanyVerifyResponse>> confirmCertificationEmail(@RequestBody SendEmailConfirmRequest request,
		@RequestHeader(required = false, value = "Authorization") String token) {

		ConfirmCompanyEmailDto certicationCompanyEmailDto = modelMapper.map(request, ConfirmCompanyEmailDto.class);
		log.debug(token);
		if (token != null) {
			UUID loginUserUuid = UUID.fromString(jwtTokenProvider.getUuidString(token.substring("Bearer ".length())));
			certicationCompanyEmailDto.setUserUuid(loginUserUuid);
		}

		CompanySimpleInfoDto companySimpleInfoDto = certificationService.confirmEmailAndUpdateCertificationCompany(modelMapper.map(request, ConfirmCompanyEmailDto.class));
		return ResponseEntity.ok(ApiResult.ofSuccess(modelMapper.map(companySimpleInfoDto, CompanyVerifyResponse.class)));
	}

}
