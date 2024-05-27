package com.fiap.burguer.dto;

import com.fiap.burguer.entities.Order;
import com.fiap.burguer.enums.StatusOrder;
import lombok.Data;

import java.util.Date;
@Data
public class CheckoutResponse {
    private int id;
    private OrderResponse order;
    private Date dateCreated;
    private StatusOrder payment_status;
    private double totalPrice;
    private String transactId;

    CheckoutResponse(int id,
                     OrderResponse order,
                     Date dateCreated,
                     StatusOrder payment_status,
                     double totalPrice,
                     String transactId){
        this.dateCreated = dateCreated;
        this.id = id;
        this.transactId = transactId;
        this.order = order;
        this.payment_status = payment_status;
        this.totalPrice = totalPrice;
    }

    public CheckoutResponse(){}
}
