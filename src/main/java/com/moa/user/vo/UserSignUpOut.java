package com.moa.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpOut {
    private String loginId;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private Boolean optionOne;
    private Boolean optionTwo;
    private Boolean agreeEmail;
    private Boolean letter;
    private Boolean dm;
    private Boolean tm;
}
