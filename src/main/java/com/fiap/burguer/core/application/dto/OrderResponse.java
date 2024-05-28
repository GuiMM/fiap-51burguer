package com.fiap.burguer.core.application.dto;
import com.fiap.burguer.adapter.driven.entities.ClientEntity;
import com.fiap.burguer.adapter.driven.entities.Product;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private int id;
    private String status;
    private double totalPrice;
    private Date dateCreated;
    private double timeWaitingOrder;
    private ClientEntity clientEntity;
    private List<Product> products;

    public OrderResponse(int id, String string, double totalPrice, Date dateCreated, double timeWaitingOrder, ClientEntity clientEntity, List<Product> products) {
    }

    public OrderResponse() {}
}
