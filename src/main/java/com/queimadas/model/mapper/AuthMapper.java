package com.queimadas.model.mapper;

import com.queimadas.config.security.service.UserDetailsEntityImpl;
import com.queimadas.controller.payload.JwtResponse;
import com.queimadas.controller.payload.JwtResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "type", constant = "Bearer")
    @Mapping(target = "token", source = "jwt")
    JwtResponse toResponse(String jwt, UserDetailsEntityImpl entity, List<String> roles);

    JwtResponseDto toResponse(JwtResponse jwtResponse);
}
