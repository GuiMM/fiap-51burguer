package com.fiap.burguer.core.application.ports;
import com.fiap.burguer.adapter.driven.entities.ProductEntity;

public interface ProductPort {
    ProductEntity findById(int id);
    void deleteById(int id);
}
