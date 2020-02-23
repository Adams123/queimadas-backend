package com.ufscar.queimadas.service;

import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException(String.format("Cannot find user %s", id.toString())));
    }

    public User findUserByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.orElseThrow(() -> new EntityNotFoundException("Failed to find user " + name));
    }

    public User createUser(String name, String password) {
        User user = new User(name, bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }
}
