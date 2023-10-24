package com.moa.user.dto;

import lombok.*;

@Builder            // 빌더 패턴의 객체 생성 코드를 자동으로 추가합니다.
@Getter              // 모든 필드에 대한 Getter 메서드를 자동으로 생성합니다.
@Setter              // 모든 필드에 대한 Setter 메서드를 자동으로 생성합니다.
@ToString            // toString 메서드를 자동으로 생성합니다.
@AllArgsConstructor  // 모든 필드 값을 파라미터로 받는 생성자를 추가합니다.
@NoArgsConstructor   // 파라미터가 없는 기본 생성자를 추가합니다.
public class UserGetDto {
    private String loginId;
    private String userName;
    private String email;
    private String phone;
    private String address;
}
