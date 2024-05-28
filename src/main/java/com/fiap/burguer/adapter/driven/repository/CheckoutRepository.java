package com.fiap.burguer.adapter.driven.repository;

import com.fiap.burguer.adapter.driven.entities.CheckOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends CrudRepository<CheckOutEntity, Integer> {
CheckOutEntity findById(int id);
    CheckOutEntity save(CheckOutEntity checkOutEntity);
}
