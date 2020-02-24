package com.ufscar.queimadas.service;

import com.ufscar.queimadas.exception.DuplicatedUserException;
import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.repository.UserRepository;
import com.ufscar.queimadas.views.UserView;
import org.springframework.context.MessageSource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static com.ufscar.queimadas.utils.Constants.DUPLICATED_USER;
import static com.ufscar.queimadas.utils.Constants.USER_NOT_FOUND;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProjectionFactory projectionFactory;
    private final MessageSource messageSource;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ProjectionFactory projectionFactory, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.projectionFactory = projectionFactory;
        this.messageSource = messageSource;
    }

    public UserView findUserById(UUID id) {
        Optional<UserView> user = userRepository.findById(id, UserView.class);
        String message = messageSource.getMessage(USER_NOT_FOUND, new Object[] {id.toString()}, Locale.getDefault());
        return user.orElseThrow(() -> new EntityNotFoundException(message));
    }

    public User findUserByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        String message = messageSource.getMessage(USER_NOT_FOUND, new Object[] {name}, Locale.getDefault());
        return user.orElseThrow(() -> new EntityNotFoundException(message));
    }

    public UserView createUser(String name, String password) throws DuplicatedUserException {
        if(userRepository.findByName(name).isPresent()) {
            String message = messageSource.getMessage(DUPLICATED_USER, null, Locale.getDefault());
            throw new DuplicatedUserException(message, name);
        }
        User user = new User(name, bCryptPasswordEncoder.encode(password));
        User createdUser = userRepository.save(user);
        return projectionFactory.createNullableProjection(UserView.class, createdUser);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
