package com.queimadas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleNotFoundException extends GenericApiException {

    private static final String ERROR_MESSAGE = "Role não encontrada";
    private static final String ERROR_MESSAGE_FORMATTED = "Role %s não encontrada";

    public RoleNotFoundException() {
        super(ERROR_MESSAGE);
    }

    public RoleNotFoundException(String role) {
        super(ERROR_MESSAGE_FORMATTED.formatted(role));
    }
}
