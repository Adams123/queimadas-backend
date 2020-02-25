package com.ufscar.queimadas.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedResponse {
    private UUID userId;
    private String token;
}
