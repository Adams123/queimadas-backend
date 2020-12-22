package com.ufscar.queimadas.service;

import com.ufscar.queimadas.controller.response.UserResponse;
import com.ufscar.queimadas.exception.DuplicatedUserException;
import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.repository.UserRepository;
import com.ufscar.queimadas.views.UserView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.login.FailedLoginException;
import java.util.*;

import static com.ufscar.queimadas.utils.Constants.DUPLICATED_USER;
import static com.ufscar.queimadas.utils.Constants.USER_NOT_FOUND;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserAuthenticationService userAuthenticationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageSource messageSource;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       MessageSource messageSource, UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userAuthenticationService = userAuthenticationService;
        this.messageSource = messageSource;
    }

    public UserView findUserById(UUID id) {
        Optional<UserView> user = userRepository.findById(id, UserView.class);
        String message = messageSource.getMessage(USER_NOT_FOUND, new Object[] {id.toString()}, Locale.getDefault());
        return user.orElseThrow(() -> new EntityNotFoundException(message));
    }

    public User findUserByName(String name) {
        Optional<User>  user = userRepository.findByName(name);
        String message = messageSource.getMessage(USER_NOT_FOUND, new Object[] {name}, Locale.getDefault());
        return user.orElseThrow(() -> new EntityNotFoundException(message));
    }

    public UserResponse createUser(String name, String password) throws DuplicatedUserException, FailedLoginException {
        if(userRepository.findByName(name).isPresent()) {
            String message = messageSource.getMessage(DUPLICATED_USER, null, Locale.getDefault());
            throw new DuplicatedUserException(message, name);
        }
        User user = new User(name, bCryptPasswordEncoder.encode(password));
        User createdUser = userRepository.save(user);
        String token = userAuthenticationService.login(name, password).getToken();
        return UserResponse.builder().token(token).userId(createdUser.getId()).build();
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
