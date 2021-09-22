package com.queimadas.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private String token;
    private UUID id; //TODO remover isso quando app estiver conseguindo usar o token
    private String username;
    private String email;
    private List<String> roles;
}