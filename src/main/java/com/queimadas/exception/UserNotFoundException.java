package com.queimadas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotFoundException extends GenericApiException {

    private static final String ERROR_MESSAGE = "Usuário %s não encontrado";

    public UserNotFoundException(String user) {
        super(ERROR_MESSAGE.formatted(user));
    }
}
