package com.fiap.burguer.adapter.driven.repository;

import com.fiap.burguer.adapter.driven.entities.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<CheckOut, Integer> {
CheckOut findById(int id);


}
