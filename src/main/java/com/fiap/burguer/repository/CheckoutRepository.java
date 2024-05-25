package com.fiap.burguer.repository;

import com.fiap.burguer.entities.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<CheckOut, Integer> {
void saveCheckOut(CheckOut checkOut);
CheckOut findById(int id);
int  countCheckOut();

}
