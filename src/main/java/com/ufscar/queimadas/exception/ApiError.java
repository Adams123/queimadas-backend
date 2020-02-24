package com.ufscar.queimadas.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
    public final String message;
    public final Exception ex;
}
