package com.ufscar.queimadas.service;

import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new EntityNotFoundException(String.format("Cannot find user %s", id.toString()));
    }

    public User createUser(String name, String password) {
        return userRepository.save(new User(name, password));
    }
}
