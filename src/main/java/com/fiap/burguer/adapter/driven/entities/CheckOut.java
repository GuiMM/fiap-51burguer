

package com.fiap.burguer.adapter.driven.entities;

import com.fiap.burguer.core.application.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "CheckOut")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @Column(name = "date_created", nullable = true)
    private Date dateCreated;

    @Column(name = "payment_status", nullable = true)
    @Enumerated(EnumType.STRING)
    private StatusOrder paymentStatus;

    @Column(name = "total_price", nullable = true)
    private double totalPrice;

    @Column(name = "transactId", nullable = true)
    private String transactId;

    @PrePersist
    public void generateTransactId() {
        if (this.transactId == null || this.transactId.isEmpty()) {
            this.transactId = UUID.randomUUID().toString();
        }
    }

}