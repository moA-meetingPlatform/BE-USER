package com.moa.user.infrastructure;


import com.moa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByLoginId(String loginId);
	Optional<User> findByUserUuid(UUID uuid);
	Boolean existsByLoginId(String loginId);

}