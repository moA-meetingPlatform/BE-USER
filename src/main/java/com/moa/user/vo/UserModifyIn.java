package com.moa.user.vo;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModifyIn {
    private String address;
    private String email;
}
