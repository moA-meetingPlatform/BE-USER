package com.moa.user.vo;

import com.example.smilekarina.agree.vo.AgreeAdvertiseIn;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserAgreeSignUpIn {
    private UserSignUpIn userSignUpIn;
    private AgreeAdvertiseIn agreeAdvertiseIn;
}
