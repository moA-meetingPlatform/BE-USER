package com.moa.user.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindIDOut {
    private String address;
    private String loginId;
}
