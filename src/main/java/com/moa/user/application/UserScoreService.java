package com.moa.user.application;


import com.moa.user.dto.kafka.ParticipantReviewCreateEventDto;


public interface UserScoreService {

	void updateUserScore(ParticipantReviewCreateEventDto dto) throws IllegalStateException;

}
