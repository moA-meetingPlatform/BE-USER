package com.moa.user.dto;


import com.moa.user.domain.UserScore;
import lombok.*;

import java.util.UUID;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserGetProfileDto {

	private UUID userUuid;
	private String nickname;
	private String userIntroduce;
	private String profileImageUrl;

	private Integer reviewerCount;

	private Double userMannersTemparature;

	private Integer userWarningScore;

	private boolean sameWithLoggedInUser;


	public void setUserScore(UserScore userScore) {
		if (userScore == null) {
			this.reviewerCount = 0;
			this.userMannersTemparature = 36.5;
			this.userWarningScore = 0;
		} else {
			this.reviewerCount = userScore.getReviewerCount();
			this.userMannersTemparature = userScore.getUserMannersTemparature();
			this.userWarningScore = userScore.getUserWarningScore();
		}
	}

}
