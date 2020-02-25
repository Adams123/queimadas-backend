package com.ufscar.queimadas.repository;

import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.model.UserBearerToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<UserBearerToken, UUID> {

    Optional<UserBearerToken> findByUser(User user);

    Optional<UserBearerToken> findByToken(UUID tokenId);

}
