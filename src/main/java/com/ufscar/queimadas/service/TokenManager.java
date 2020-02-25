package com.ufscar.queimadas.service;

import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.model.UserBearerToken;
import com.ufscar.queimadas.repository.TokenRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenManager {

    private final TokenRepository tokenRepository;

    public TokenManager(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Optional<User> findByToken(UUID token) {
        Optional<UserBearerToken> userToken = tokenRepository.findByToken(token);
        if(!userToken.isPresent())
            throw new EntityNotFoundException("Token not found"); //TODO localize
        return Optional.ofNullable((userToken.get().getUser()));
    }

    public UUID createToken(User user) {
        Optional<UserBearerToken> userOpt = tokenRepository.findByUser(user);
        if(userOpt.isPresent()){
            UserBearerToken existentUser = userOpt.get();
            if(!existentUser.isExpired() && existentUser.getValidUntil().isAfter(LocalDateTime.now())){
                return existentUser.getToken();
            }
        }
        UserBearerToken created = tokenRepository.save(new UserBearerToken(user, UUID.randomUUID().toString()));
        return created.getToken();
    }

    //Used on logout
    public void revokeToken(User user) {
        Optional<UserBearerToken> token = tokenRepository.findByUser(user);
        if(token.isPresent()) {
            token.get().setExpired(true);
            tokenRepository.save(token.get());
        } else {
            throw new EntityNotFoundException("Token not found"); //TODO localize
        }
    }
}
