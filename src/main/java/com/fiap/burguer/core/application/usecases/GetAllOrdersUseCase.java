package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllOrdersUseCase {
    private final OrderPort orderPort;

    public GetAllOrdersUseCase(OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    public List<Order> getAllOrders() {
        List<Order> orderEntities = orderPort.findAll();

        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new ResourceNotFoundException("Não existem pedidos ainda");
        }

        return orderEntities.stream()
                .filter(order -> order.getStatus() == StatusOrder.RECEIVED ||
                        order.getStatus() == StatusOrder.PREPARATION ||
                        order.getStatus() == StatusOrder.READY)
                .sorted((o1, o2) -> {
                    int statusComparison = getStatusPriority(o1.getStatus()) - getStatusPriority(o2.getStatus());
                    if (statusComparison != 0) {
                        return statusComparison;
                    } else {
                        return o1.getDateCreated().compareTo(o2.getDateCreated());
                    }
                })
                .collect(Collectors.toList());
    }

    private int getStatusPriority(StatusOrder status) {
        return switch (status) {
            case READY -> 1;
            case PREPARATION -> 2;
            case RECEIVED -> 3;
            default -> Integer.MAX_VALUE;
        };
    }
}
