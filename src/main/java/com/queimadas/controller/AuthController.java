package com.queimadas.controller;

import com.queimadas.config.security.jwt.JwtUtils;
import com.queimadas.controller.payload.JwtResponse;
import com.queimadas.controller.payload.JwtResponseDto;
import com.queimadas.controller.payload.LoginRequest;
import com.queimadas.controller.payload.SignupRequest;
import com.queimadas.exception.EmailAlreadyExistsException;
import com.queimadas.exception.RoleNotFoundException;
import com.queimadas.exception.UsernameAlreadyExistsException;
import com.queimadas.model.mapper.AuthMapper;
import com.queimadas.repository.RoleRepository;
import com.queimadas.repository.UserRepository;
import com.queimadas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDto> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {

        JwtResponse jwtResponse = authService.authenticate(loginRequest);
        return ResponseEntity.ok(authMapper.toResponse(jwtResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDto> registerUser(
            @Valid @RequestBody SignupRequest signUpRequest
    )
            throws EmailAlreadyExistsException, UsernameAlreadyExistsException, RoleNotFoundException {
        JwtResponse jwtResponse = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(authMapper.toResponse(jwtResponse));
    }
}