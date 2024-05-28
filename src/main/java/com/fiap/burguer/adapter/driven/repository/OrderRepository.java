package com.fiap.burguer.adapter.driven.repository;
import com.fiap.burguer.adapter.driven.entities.Order;
import com.fiap.burguer.core.application.enums.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findByStatus(StatusOrder status);
     Order getById(int id);
}