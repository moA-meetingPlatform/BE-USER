package com.moa.user.infrastructure.kafka.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.application.UserScoreService;
import com.moa.user.dto.kafka.ParticipantReviewCreateEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class ParticipantReviewCreateEventConsumer {

	private final ObjectMapper objectMapper;
	private final UserScoreService userScoreService;


	@KafkaListener(topics = "participate-review-create", groupId = "participate-review-create")
	public void consume(String message) {
		log.debug(String.format("Consumed message : %s", message));

		Map<Object, Object> map;

		try {
			map = objectMapper.readValue(message, Map.class);
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException : {}", e.getMessage() + "\n" + message);
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		log.debug("여까진 됨");

		ParticipantReviewCreateEventDto dto = new ParticipantReviewCreateEventDto(
			UUID.fromString(String.valueOf(map.get("reviewTargetUserUuid"))),
			(int) map.get("rating")
		);

		log.debug("dto : {}", dto);
		try {
			userScoreService.updateUserScore(dto);
		} catch (Exception e) {
			log.error("updateUserScore Exception : {}", e.getMessage());
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

}
