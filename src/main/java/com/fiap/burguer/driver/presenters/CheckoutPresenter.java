package com.fiap.burguer.driver.presenters;

import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutPresenter {

    public static CheckoutResponse mapCheckoutToResponse(CheckOut checkOut) {
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
