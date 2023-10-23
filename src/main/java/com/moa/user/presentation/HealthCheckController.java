package com.moa.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.moa.user.common.ApiResult;

@RestController
@RequestMapping("/api/v1/meeting")
@Slf4j
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/test")
    public ResponseEntity<ApiResult<String>> test() {
        return ResponseEntity.ok(ApiResult.ofSuccess("meeting test"));
    }

}