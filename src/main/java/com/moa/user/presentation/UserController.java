package com.moa.user.presentation;


import com.moa.user.application.UserService;
import com.moa.user.dto.UserSignUpDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "User Controller", description = "유저 관련 API")
// @RestController 어노테이션은 이 클래스가 REST API를 처리하는 컨트롤러임을 나타냅니다.
// Spring에서는 이 어노테이션을 사용하여 HTTP 요청을 처리하는 메서드를 포함하는 클래스를 정의할 수 있습니다.
@RestController
// @RequestMapping 어노테이션은 이 컨트롤러의 기본 URL 경로를 설정합니다.
// 이 경우, 이 컨트롤러의 모든 엔드포인트는 "/api/v1"로 시작하게 됩니다.
@RequestMapping("/api/v1/user")
// @Slf4j 어노테이션은 Lombok 라이브러리를 사용하여 이 클래스에 로깅 기능을 추가합니다.
// 이 어노테이션 덕분에, 이 클래스 내에서 'log' 객체를 사용하여 로그 메시지를 기록할 수 있게 됩니다.
@Slf4j
// @RequiredArgsConstructor 어노테이션은 Lombok 라이브러리의 일부로써,
// 이 클래스의 모든 final 필드 또는 @NonNull 어노테이션이 붙은 필드에 대한 생성자를 자동으로 생성해줍니다.
// 주로 의존성 주입(Dependency Injection)에 사용됩니다.
@RequiredArgsConstructor
// @CrossOrigin 어노테이션은 CORS(Cross-Origin Resource Sharing) 설정을 제공합니다.
// "origins" 속성은 모든 도메인에서 이 API에 액세스할 수 있도록 허용하며,
// "allowedHeaders" 속성은 모든 헤더를 허용하는 CORS 정책을 설정합니다.
// 이 설정은 주로 웹 브라우저에서 다른 도메인에 있는 이 API에 직접 요청을 보낼 때 사용됩니다.
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Transactional
    @PostMapping("/user/join/cert")
    public ResponseEntity<?> createUser(@RequestBody UserAgreeSignUpIn userAgreeSignUpIn) {
        Long userId = userService.createUser(modelMapper.map(userAgreeSignUpIn.getUserSignUpIn(), UserSignUpDto.class));
        agreeService.createAgreeAdvertiseByUser(userId,
                modelMapper.map(userAgreeSignUpIn.getAgreeAdvertiseIn(), AgreeAdvertiseDto.class));
        cardService.registerOnlinePointCard(userId);
        return ResponseEntity.ok(ResponseOut.success(userSignUpOutCreate(userAgreeSignUpIn)));
    }

}
