

package com.fiap.burguer.entities;

import com.fiap.burguer.enums.StatusOrder;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "CheckOut")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CheckOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private int order;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOrder payment_status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "transactId", nullable = false)
    @Enumerated(EnumType.STRING)
    private String transactId;

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public StatusOrder getPayment_status() {
        return payment_status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getTransactId() {
        return transactId;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setPayment_status(StatusOrder payment_status) {
        this.payment_status = payment_status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTransactId(String transactId) {
        this.transactId = transactId;
    }
}

