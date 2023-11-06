package com.moa.user.infrastructure;


import com.moa.user.domain.User;
import com.moa.user.domain.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserScoreRepository extends JpaRepository<UserScore, Long> {

	Optional<UserScore> findByUser(User user);

}
