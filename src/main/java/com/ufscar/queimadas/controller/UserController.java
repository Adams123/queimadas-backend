package com.ufscar.queimadas.controller;

import com.ufscar.queimadas.exception.DuplicatedUserException;
import com.ufscar.queimadas.service.UserService;
import com.ufscar.queimadas.views.UserView;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

import static com.ufscar.queimadas.utils.Constants.DELETED_USER;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserView> createUser(@RequestParam  String username, @RequestParam String password) throws DuplicatedUserException {
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
}
