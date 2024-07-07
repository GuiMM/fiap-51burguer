package com.fiap.burguer.core.application.Exception;

import org.springframework.http.ResponseEntity;

public class ImpossibleToCheckoutException extends RuntimeException{

    public ImpossibleToCheckoutException(String errorMessage){
        super(errorMessage);
    }
}
