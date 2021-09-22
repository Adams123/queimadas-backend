package com.queimadas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GenericApiException extends Exception {

    public GenericApiException() {
        super();
    }

    public GenericApiException(String message) {
        super(message);
    }
}
