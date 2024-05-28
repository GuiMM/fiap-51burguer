package com.fiap.burguer.core.application.dto;
import com.fiap.burguer.adapter.driven.entities.ClientEntity;
import com.fiap.burguer.adapter.driven.entities.ProductEntity;
import com.fiap.burguer.core.domain.Client;
import com.fiap.burguer.core.domain.Product;
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
    private Client client;
    private List<Product> products;

    public OrderResponse(int id, String string, double totalPrice, Date dateCreated, double timeWaitingOrder, Client client, List<Product> products) {
    }

    public OrderResponse() {}
}
