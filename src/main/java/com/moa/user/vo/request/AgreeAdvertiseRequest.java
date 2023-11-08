package com.moa.user.vo.request;


import lombok.Getter;


@Getter
public class AgreeAdvertiseRequest {

	private boolean emailNotificationStatus;
	private boolean smsNotificationStatus;
	private boolean pushNotificationStatus;

}
