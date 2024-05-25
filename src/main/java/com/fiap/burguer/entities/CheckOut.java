

package com.fiap.burguer.entities;

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
    private Integer order;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private String payment_status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "transactId", nullable = false)
    @Enumerated(EnumType.STRING)
    private String transactId;
}

