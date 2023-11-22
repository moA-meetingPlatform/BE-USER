package com.moa.user.application;


import com.moa.user.domain.User;
import com.moa.user.domain.UserScore;
import com.moa.user.dto.kafka.ParticipantReviewCreateEventDto;
import com.moa.user.infrastructure.UserRepository;
import com.moa.user.infrastructure.UserScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserScoreServiceImpl implements UserScoreService {

	private final UserScoreRepository userScoreRepository;
	private final UserRepository userRepository;


	/**
	 * 사용자의 평점을 업데이트한다.
	 * 카프카 consumer에서 호출된다.
	 *
	 * @param dto
	 */
	@Override
	@Transactional
	public void updateUserScore(ParticipantReviewCreateEventDto dto) throws IllegalStateException {
		double userMannersTemparature = switch (dto.getRating()) {
			case 1 -> -0.1;
			case 2 -> -0.05;
			case 3 -> 0;
			case 4 -> 0.05;
			case 5 -> 0.01;
			default -> throw new IllegalStateException("Unexpected value: " + dto.getRating());
		};
		User user = userRepository.findByUserUuid(dto.getReviewTargetUserUuid())
			.orElseThrow(() -> new IllegalStateException("존재하지 않는 유저"));

		UserScore userScore = userScoreRepository.findByUser(user)
			.orElseGet(() -> {
				UserScore newUserScore = new UserScore(user);
				userScoreRepository.save(newUserScore); // 새로운 UserScore 저장
				return newUserScore;
			});

		userScore.updateUserTemparatureAndReviewerCount(userMannersTemparature);
	}

}

