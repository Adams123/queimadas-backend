package com.queimadas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsernameAlreadyExistsException extends GenericApiException {

    private static final String ERROR_MESSAGE = "Nome de usuário já está em uso!";

    public UsernameAlreadyExistsException() {
        super(ERROR_MESSAGE);
    }
}
