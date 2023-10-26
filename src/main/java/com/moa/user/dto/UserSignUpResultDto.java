package com.moa.user.dto;


import lombok.*;

import java.util.UUID;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResultDto {

	private UUID useruuid;

}
