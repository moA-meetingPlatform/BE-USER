package com.moa.user.dto.kafka;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@Getter
@AllArgsConstructor
public class ParticipantReviewCreateEventDto {

	private UUID reviewTargetUserUuid;
	private int rating;

}
