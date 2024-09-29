package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;

public class GetOrderByIdUseCase {
    private final OrderPort orderPort;

    public GetOrderByIdUseCase(OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    public Order getOrderById(int id) {
        Order order = orderPort.findById(id);
        if (order == null) {
            throw new ResourceNotFoundException("Pedido não encontrado");
        }
        return order;
    }
}
