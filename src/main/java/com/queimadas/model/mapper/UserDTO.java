package com.queimadas.model.mapper;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserDTO {
    private String username;
    private String email;
}