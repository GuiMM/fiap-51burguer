package com.fiap.burguer.adapter.driven.repository;
import com.fiap.burguer.adapter.driven.entities.OrderEntity;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository  extends CrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findByStatus(StatusOrder status);
    OrderEntity save(OrderEntity orderEntity);
    OrderEntity findById(int id);
    void deleteById(int id);
    List<OrderEntity> findAll();
}