package com.moa.user.dto;

import lombok.*;

// Lombok 라이브러리를 사용하여 반복적인 코드를 줄입니다.
@Builder            // 빌더 패턴의 객체 생성 코드를 자동으로 추가합니다.
@Getter              // 모든 필드에 대한 Getter 메서드를 자동으로 생성합니다.
@Setter              // 모든 필드에 대한 Setter 메서드를 자동으로 생성합니다.
@ToString            // toString 메서드를 자동으로 생성합니다.
@AllArgsConstructor  // 모든 필드 값을 파라미터로 받는 생성자를 추가합니다.
@NoArgsConstructor   // 파라미터가 없는 기본 생성자를 추가합니다.
public class LoginDto {

    // 사용자의 고유 식별자입니다.
    String UUID;

    // 사용자의 이름입니다.
    String userName;

    // 사용자 인증에 사용되는 토큰입니다.
    String token;
}
