package com.fiap.burguer.infraestructure.repository;

import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends CrudRepository<CheckOutEntity, Integer> {
    CheckOutEntity findById(int id);
    CheckOutEntity save(CheckOutEntity checkOutEntity);
}
