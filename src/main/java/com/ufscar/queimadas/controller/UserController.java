package com.ufscar.queimadas.controller;

import com.ufscar.queimadas.model.User;
import com.ufscar.queimadas.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestParam  String username, @RequestParam String password) {
        return userService.createUser(username, password);
    }

    @GetMapping("/find")
    public User findUser(@RequestParam UUID id) {
        return userService.findUserById(id);
    }
}
