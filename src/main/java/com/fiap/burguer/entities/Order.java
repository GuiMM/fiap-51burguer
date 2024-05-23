package com.fiap.burguer.entities;
import com.fiap.burguer.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity(name = "`order`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "time_waiting_order", nullable = false)
    private Integer timeWaitingOrder;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOrder status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItensList;

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

    public Date getCreationDate() {
        return dateCreated;
    }

    public void setCreationDate(Date dateCreated) {
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

    public List<OrderItem> getOrderItensList() {
        return orderItensList;
    }

    public void setOrderItemsList(List<OrderItem> orderItensList) {
        this.orderItensList = orderItensList;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
