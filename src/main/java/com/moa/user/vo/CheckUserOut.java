package com.moa.user.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Builder
@Setter
public class CheckUserOut {
    private String userName;
    private String userLoginId;
    private String UUID;
}
