package com.fiap.burguer.adapter.driven.repository;

import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findById(int id);
    void deleteById(int id);
}
