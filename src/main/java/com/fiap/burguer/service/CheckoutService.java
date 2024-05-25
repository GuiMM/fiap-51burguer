package com.fiap.burguer.service;
import com.fiap.burguer.entities.CheckOut;
import com.fiap.burguer.repository.CheckoutRepository;
import org.springframework.stereotype.Service;

// Essa classe, é para realizar uma requisição consultará o checkout, através do ID do pedido
import com.fiap.burguer.entities.CheckOut;
import com.fiap.burguer.repository.CheckoutRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckoutService {
    private final CheckoutRepository checkoutRepository;

    public CheckoutService(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    public CheckOut findById(int id) {
        return checkoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checkout not found with id: " + id));
    }
}

