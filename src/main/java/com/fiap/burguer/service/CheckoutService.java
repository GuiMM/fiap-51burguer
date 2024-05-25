package com.fiap.burguer.service;
import com.fiap.burguer.entities.CheckOut;
import com.fiap.burguer.repository.CheckoutRepository;

// Essa classe, é para realizar uma requisição consultará o checkout, através do ID do pedido
public class CheckoutService {
    private final CheckoutRepository checkoutRepository;
    public CheckoutService(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    public CheckOut findById(int id) {
        return checkoutRepository.findById(id);
    }
}
