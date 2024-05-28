package com.fiap.burguer.core.application.service;

import com.fiap.burguer.adapter.driven.adapters.CheckOutAdapter;
import com.fiap.burguer.adapter.driven.entities.Order;
import com.fiap.burguer.adapter.driven.entities.OrderItem;
import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import com.fiap.burguer.core.application.dto.CheckoutResponse;
import com.fiap.burguer.core.application.dto.OrderResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.adapter.driven.repository.CheckoutRepository;
import com.fiap.burguer.adapter.driven.repository.OrderRepository;
import com.fiap.burguer.adapter.driven.entities.CheckOutEntity;
import com.fiap.burguer.core.domain.CheckOut;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutService {
    private final CheckOutAdapter checkOutAdapter;
    private final OrderRepository orderRepository;
    private final IPaymentGateway paymentGateway;

    public CheckoutService(CheckOutAdapter checkOutAdapter, OrderRepository orderRepository, IPaymentGateway paymentGateway) {
        this.checkOutAdapter = checkOutAdapter;
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    public CheckOut findById(int id) {
        return checkOutAdapter.findById(id);
    }

    public Order findOrderById(int id) {
        return orderRepository.getById(id);
    }

    public void updateStatusOrder(Order order, StatusOrder statusOrder) {
        if (paymentGateway.processPayment(statusOrder)) {
            order.setStatus(StatusOrder.RECEIVED);

        } else {
            order.setStatus(StatusOrder.WAITINGPAYMENT);

        }
        orderRepository.save(order);

    }

    public CheckOut save(CheckOutEntity checkOutEntity) {
        return checkOutAdapter.save(checkOutEntity);
    }

    public CheckOutEntity mapOrderToCheckout(Order order, StatusOrder statusOrder) {
        CheckOutEntity checkoutNew = new CheckOutEntity();
        checkoutNew.setDateCreated(new Date());
        checkoutNew.setOrder(order);
        checkoutNew.setTotalPrice(order.getTotalPrice());
        checkoutNew.setTransactId(checkoutNew.getTransactId());
        checkoutNew.setPaymentStatus(StatusOrder.APPROVEDPAYMENT);
        return checkoutNew;
    }

    public CheckoutResponse mapCheckoutToResponse(CheckOutEntity checkOutEntity) {
        if (checkOutEntity == null) {
            return null;
        }
        double totalPrice = 0.0;
        Integer timeWaitingOrder = 0;
        Date date = new Date();
        List<ProductEntity> productEntities = new ArrayList<>();

        for (OrderItem item : checkOutEntity.getOrder().getOrderItemsList()) {
            totalPrice += item.getProductPrice() * item.getAmount();
            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
            timeWaitingOrder += (preparationTime * item.getAmount());
            date = item.getOrder().getDateCreated();
            productEntities.add(item.getProductEntity());
        }
        OrderResponse responseOrder = new OrderResponse();
        responseOrder.setId(checkOutEntity.getOrder().getId());
        responseOrder.setStatus(checkOutEntity.getOrder().getStatus() != null ? checkOutEntity.getOrder().getStatus().toString() : null);
        responseOrder.setTotalPrice(checkOutEntity.getOrder().getTotalPrice());
        responseOrder.setTimeWaitingOrder(checkOutEntity.getOrder().getTimeWaitingOrder());
        responseOrder.setDateCreated(date);
        responseOrder.setProductEntities(productEntities);

        if (checkOutEntity.getOrder().getClientEntity() != null) {
            responseOrder.setClientEntity(checkOutEntity.getOrder().getClientEntity());
        }

        CheckoutResponse responseCheckout = new CheckoutResponse();
        responseCheckout.setOrder(responseOrder);
        responseCheckout.setTransactId(checkOutEntity.getTransactId());
        responseCheckout.setId(checkOutEntity.getId());
        responseCheckout.setPayment_status(checkOutEntity.getPaymentStatus());
        responseCheckout.setDateCreated(checkOutEntity.getDateCreated());
        responseCheckout.setTotalPrice(checkOutEntity.getTotalPrice());
        return responseCheckout;
    }
}

