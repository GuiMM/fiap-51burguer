package com.fiap.burguer.adapter.driver.handlers;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
