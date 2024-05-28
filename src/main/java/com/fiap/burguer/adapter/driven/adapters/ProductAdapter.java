package com.fiap.burguer.adapter.driven.adapters;

import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import com.fiap.burguer.core.application.ports.ProductPort;

public class ProductAdapter implements ProductPort {

    @Override
    public ProductEntity findById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
