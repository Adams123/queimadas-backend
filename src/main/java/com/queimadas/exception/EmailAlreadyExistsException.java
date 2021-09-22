package com.queimadas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailAlreadyExistsException extends GenericApiException {

    private static final String ERROR_MESSAGE = "Email já está em uso!";

    public EmailAlreadyExistsException() {
        super(ERROR_MESSAGE);
    }
}
