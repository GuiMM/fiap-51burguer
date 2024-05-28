package com.fiap.burguer.adapter.driven.repository;

import com.fiap.burguer.adapter.driven.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);
    void deleteById(int id);
}
