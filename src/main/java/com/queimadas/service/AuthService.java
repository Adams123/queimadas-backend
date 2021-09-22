package com.queimadas.service;

import com.queimadas.config.security.jwt.JwtUtils;
import com.queimadas.config.security.service.UserDetailsEntityImpl;
import com.queimadas.controller.payload.JwtResponse;
import com.queimadas.controller.payload.LoginRequest;
import com.queimadas.controller.payload.SignupRequest;
import com.queimadas.exception.EmailAlreadyExistsException;
import com.queimadas.exception.RoleNotFoundException;
import com.queimadas.exception.UsernameAlreadyExistsException;
import com.queimadas.model.ERole;
import com.queimadas.model.Role;
import com.queimadas.model.User;
import com.queimadas.model.mapper.AuthMapper;
import com.queimadas.repository.RoleRepository;
import com.queimadas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.queimadas.model.ERole.ROLE_ADMIN;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthMapper authMapper;

    public JwtResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsEntityImpl userDetails = (UserDetailsEntityImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return authMapper.toResponse(jwt, userDetails, roles);
    }

    public JwtResponse registerUser(SignupRequest signUpRequest) throws RoleNotFoundException, EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            throw new UsernameAlreadyExistsException();
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new EmailAlreadyExistsException();
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = processRoles(signUpRequest);

        user.setRoles(roles);
        userRepository.save(user);

        return authenticate(new LoginRequest(signUpRequest.getUsername(), signUpRequest.getPassword()));
    }

    private Set<Role> processRoles(SignupRequest signUpRequest) throws RoleNotFoundException {
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(RoleNotFoundException::new);
            roles.add(userRole);
        } else {
            for (String role : strRoles) {
                switch (role) {
                    case "ROLE_ADMIN" -> {
                        Role adminRole = roleRepository.findByName(ROLE_ADMIN)
                                .orElseThrow(RoleNotFoundException::new);
                        roles.add(adminRole);
                    }
                    case "ROLE_MODERATOR" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(RoleNotFoundException::new);
                        roles.add(modRole);
                    }
                    default -> {
                        if (!EnumUtils.isValidEnum(ERole.class, role)) {
                            throw new RoleNotFoundException(role);
                        }
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(RoleNotFoundException::new);
                        roles.add(userRole);
                    }
                }
            }
        }
        return roles;
    }

}
