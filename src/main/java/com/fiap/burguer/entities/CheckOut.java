

package com.fiap.burguer.entities;

import com.fiap.burguer.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Entity(name = "CheckOut")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CheckOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private int order;

    @Setter
    @Column(name = "date_created")
    private Date dateCreated;

    @Setter
    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOrder payment_status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Setter
    @Column(name = "transactId", nullable = false)
    @Enumerated(EnumType.STRING)
    private String transactId;

}

