package com.moa.user.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSignUpIn {
    private String loginId;
    private String password;
    private String userName;
    private String email;
    private String phone;
    private String address;
}
