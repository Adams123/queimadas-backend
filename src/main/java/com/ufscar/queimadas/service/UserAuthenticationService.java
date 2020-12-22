package com.ufscar.queimadas.service;

import com.ufscar.queimadas.controller.response.UserResponse;
import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthenticationService {
    private final TokenManager tokenManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserAuthenticationService(TokenManager tokenManager, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.tokenManager = tokenManager;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserResponse login(final String username, final String password) throws FailedLoginException {
        Optional<User> user = userRepository.findByName(username);
        if(user.isPresent()){
            if(bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
                return UserResponse.builder().token(tokenManager.createToken(user.get()).toString()).userId(user.get().getId()).build();
            } else {
                throw new FailedLoginException("Invalid user name and password");
            }
        }
        else {
            throw new FailedLoginException("Username not found");
        }
    }

    public Optional<User> findByToken(final String token) {
        return tokenManager.findByToken(UUID.fromString(token));
    }

    public void logout(User user) {
        tokenManager.revokeToken(user);
    }
}
