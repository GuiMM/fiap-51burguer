package com.fiap.burguer.adapter.driver.handlers;

public class InvalidCPFException extends RuntimeException {
    public InvalidCPFException(String message) {
        super(message);
    }
}