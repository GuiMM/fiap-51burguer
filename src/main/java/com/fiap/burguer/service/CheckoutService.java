package com.fiap.burguer.service;

import com.fiap.burguer.entities.CheckOut;
import com.fiap.burguer.entities.Product;
import com.fiap.burguer.repository.CheckoutRepository;
import com.fiap.burguer.repository.ProductRepository;

public class CheckoutService {
    private final CheckoutRepository checkoutRepository;
    public CheckoutService(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    public CheckOut findById(int id) {
        return checkoutRepository.findById(id);
    }
}
