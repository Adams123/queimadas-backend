package com.ufscar.queimadas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class DuplicatedUserException extends Exception {
    private final String message;
    private final String name;
    public DuplicatedUserException(String message, String name) {
        super();
        this.message = message;
        this.name = name;
    }
}
