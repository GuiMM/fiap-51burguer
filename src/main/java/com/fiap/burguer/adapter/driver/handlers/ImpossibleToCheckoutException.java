package com.fiap.burguer.adapter.driver.handlers;

import org.springframework.http.ResponseEntity;

public class ImpossibleToCheckoutException extends RuntimeException{

    public ImpossibleToCheckoutException(String errorMessage){
        super(errorMessage);
    }
}
