package com.fiap.burguer.dto;

import com.fiap.burguer.entities.Product;
import com.fiap.burguer.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private int id;
    private String status;
    private double totalPrice;
    private double timeWaitingOrder;
    private List<Product> products;

    public OrderResponse(int id, String string, double totalPrice, double timeWaitingOrder, List<Product> products) {
    }

    public OrderResponse() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTimeWaitingOrder() {
        return timeWaitingOrder;
    }

    public void setTimeWaitingOrder(double timeWaitingOrder) {
        this.timeWaitingOrder = timeWaitingOrder;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
