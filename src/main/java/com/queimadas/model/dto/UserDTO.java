package com.queimadas.model.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserDTO {
    private String username;
    private String email;
    private UUID id;
    private String token;
}