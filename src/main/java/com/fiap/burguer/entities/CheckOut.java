

package com.fiap.burguer.entities;

import com.fiap.burguer.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private StatusOrder payment_status;

    @Column(name = "total_price", nullable = true)
    private double totalPrice;

    @Column(name = "transactId", nullable = true)
    private String transactId;

}

