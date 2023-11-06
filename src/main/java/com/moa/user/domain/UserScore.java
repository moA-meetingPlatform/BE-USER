package com.moa.user.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_score")
public class UserScore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "reviewer_count")
	private Integer reviewerCount;

	@Column(name = "user_manners_temparature")
	private Integer userMannersTemparature;

	@Column(name = "user_warning_score")
	private Integer userWarningScore;


	public UserScore(User user) {
		this.user = user;
		this.reviewerCount = 0;
		this.userMannersTemparature = 0;
		this.userWarningScore = 0;
	}

}
