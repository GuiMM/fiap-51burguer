package com.fiap.burguer.core.application.service;
import com.fiap.burguer.adapter.driven.adapters.CheckOutAdapter;
import com.fiap.burguer.adapter.driven.adapters.OrderAdapter;
import com.fiap.burguer.adapter.driven.adapters.PaymentGateway;
import com.fiap.burguer.adapter.driver.handlers.ImpossibleToCheckoutException;
import com.fiap.burguer.core.application.dto.CheckoutResponse;
import com.fiap.burguer.core.application.dto.OrderResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutService {
    private final CheckOutAdapter checkOutAdapter;
    private final OrderAdapter orderAdapter;
    private final PaymentGateway paymentGateway;

    public CheckoutService(CheckOutAdapter checkOutAdapter, OrderAdapter orderAdapter, PaymentGateway paymentGateway) {
        this.checkOutAdapter = checkOutAdapter;
        this.orderAdapter = orderAdapter;
        this.paymentGateway = paymentGateway;
    }

    public CheckOut findById(int id) {
        return checkOutAdapter.findById(id);
    }

    public Order findOrderById(int id) {
        return orderAdapter.findById(id);
    }

    public void updateStatusOrder(Order order, StatusOrder statusOrder) {
        if (paymentGateway.processPayment(statusOrder)) {
            order.setStatus(StatusOrder.RECEIVED);

        } else {
            order.setStatus(StatusOrder.WAITINGPAYMENT);

        }
        orderAdapter.save(order);

    }

    public CheckOut save(CheckOut checkOut) {
        return checkOutAdapter.save(checkOut);
    }


    public CheckOut createCheckout(int id, StatusOrder statusOrder){
        Order order = this.findOrderById(id);
        if(order.getStatus() == StatusOrder.RECEIVED|| order.getStatus() == StatusOrder.CANCELED){
            String errorMessage = "Não foi possível realizar o pagamento, pois o pedido " + order.getId() + " já está com o status "+ order.getStatus() ;
            throw new ImpossibleToCheckoutException(errorMessage);
        }else{
            this.updateStatusOrder(order, statusOrder);
        }
        CheckOut checkoutNew = this.save(this.mapOrderToCheckout(order, statusOrder));

        if (checkoutNew == null) {
            throw new RuntimeException("Não foi possível processar a solicitação");
        }
        return checkoutNew;
    }

    public CheckOut mapOrderToCheckout(Order order, StatusOrder statusOrder) {
        CheckOut checkoutNew = new CheckOut();
        checkoutNew.setDateCreated(new Date());
        checkoutNew.setOrder(order);
        checkoutNew.setTotalPrice(order.getTotalPrice());
        checkoutNew.setTransactId(checkoutNew.getTransactId());
        checkoutNew.setPaymentStatus(StatusOrder.APPROVEDPAYMENT);
        return checkoutNew;
    }

    public CheckoutResponse mapCheckoutToResponse(CheckOut checkOut) {
        if (checkOut == null) {
            return null;
        }
        double totalPrice = 0.0;
        Integer timeWaitingOrder = 0;
        Date date = new Date();
        List<Product> products = new ArrayList<>();

        for (OrderItem item : checkOut.getOrder().getOrderItemsList()) {
            totalPrice += item.getTotalProductPrice() * item.getAmount();
            Integer preparationTime = Integer.parseInt(item.getPreparationTime());
            timeWaitingOrder += (preparationTime * item.getAmount());
            date = item.getOrder().getDateCreated();
            products.add(item.getProduct());
        }
        OrderResponse responseOrder = new OrderResponse();
        responseOrder.setId(checkOut.getOrder().getId());
        responseOrder.setStatus(checkOut.getOrder().getStatus() != null ? checkOut.getOrder().getStatus().toString() : null);
        responseOrder.setTotalPrice(checkOut.getOrder().getTotalPrice());
        responseOrder.setTimeWaitingOrder(checkOut.getOrder().getTimeWaitingOrder());
        responseOrder.setDateCreated(date);
        responseOrder.setProducts(products);

        if (checkOut.getOrder().getClient() != null) {
            responseOrder.setClient(checkOut.getOrder().getClient());
        }

        CheckoutResponse responseCheckout = new CheckoutResponse();
        responseCheckout.setOrder(responseOrder);
        responseCheckout.setTransactId(checkOut.getTransactId());
        responseCheckout.setId(checkOut.getId());
        responseCheckout.setPayment_status(checkOut.getPaymentStatus());
        responseCheckout.setDateCreated(checkOut.getDateCreated());
        responseCheckout.setTotalPrice(checkOut.getTotalPrice());
        return responseCheckout;
    }
}

