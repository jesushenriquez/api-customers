package com.jesushenriquez.testing.components.exceptions;

import lombok.Getter;

import java.io.Serial;

/**
 * CustomerNotFoundException
 */
@Getter
public class CustomerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7005849637616672680L;
    private final String errorMessage;

    public CustomerNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

}
