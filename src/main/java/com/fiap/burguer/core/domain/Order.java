package com.fiap.burguer.core.domain;

import com.fiap.burguer.adapter.driven.entities.ClientEntity;
import com.fiap.burguer.core.application.enums.StatusOrder;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {

    private int id;

    private Integer timeWaitingOrder;

    private Date dateCreated;

    private StatusOrder status;

    private double totalPrice;

    private ClientEntity clientEntity;

    private List<OrderItem> orderItemsList;

    public Integer getTimeWaitingOrder() {
        return timeWaitingOrder;
    }

    public void setTimeWaitingOrder(Integer timeWaitingOrder) {
        this.timeWaitingOrder = timeWaitingOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    public List<OrderItem> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }
}
