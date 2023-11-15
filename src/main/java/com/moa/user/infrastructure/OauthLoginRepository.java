package com.moa.user.infrastructure;


import com.moa.user.domain.OauthLogin;
import com.moa.user.domain.OauthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OauthLoginRepository extends JpaRepository<OauthLogin, Long> {

	Optional<OauthLogin> findByOauthProviderAndOauthUserId(OauthProviderType oauthProviderType, String oauthUserId);
	boolean existsByOauthProviderAndOauthUserId(OauthProviderType oauthProviderType, String oauthUserId);

}
