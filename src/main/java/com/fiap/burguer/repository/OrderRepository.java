package com.fiap.burguer.repository;
import com.fiap.burguer.entities.Order;
import com.fiap.burguer.enums.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findByStatus(StatusOrder status);
     Order getById(int id);
}