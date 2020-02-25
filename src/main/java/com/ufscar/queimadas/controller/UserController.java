package com.ufscar.queimadas.controller;

import com.ufscar.queimadas.controller.response.UserCreatedResponse;
import com.ufscar.queimadas.exception.DuplicatedUserException;
import com.ufscar.queimadas.service.UserAuthenticationService;
import com.ufscar.queimadas.service.UserService;
import com.ufscar.queimadas.views.UserView;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static com.ufscar.queimadas.utils.Constants.DELETED_USER;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final UserAuthenticationService userAuthenticationService;

    public UserController(UserService userService, MessageSource messageSource, UserAuthenticationService userAuthenticationService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/public/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserCreatedResponse> createUser(@RequestParam  String username, @RequestParam String password) throws DuplicatedUserException, FailedLoginException {
        return ResponseEntity.ok(userService.createUser(username, password));
    }

    @GetMapping("/find")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<UserView> findUser(@RequestParam UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> deleteUser(@RequestParam UUID id) {
        userService.delete(id);
        String message = messageSource.getMessage(DELETED_USER, null, Locale.getDefault());
        return ResponseEntity.ok(message);
    }

    @PostMapping("/public/login")
    public ResponseEntity<UUID> login(@RequestParam  String username, @RequestParam String password) throws DuplicatedUserException, FailedLoginException {
        Optional<UUID> loginToken = userAuthenticationService.login(username, password);
        if(loginToken.isPresent()) {
            return ResponseEntity.ok(loginToken.get());
        } else {
            throw new DuplicatedUserException("Failed", username); //TODO fix and localize
        }
    }
}
