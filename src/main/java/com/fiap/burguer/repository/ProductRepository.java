package com.fiap.burguer.repository;

import com.fiap.burguer.entities.Category;
import com.fiap.burguer.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(int id);
    void deleteById(int id);
}
