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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOrder status;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemsList;

    public Order(Integer timeWaitingOrder, Date dateCreated, StatusOrder status, double totalPrice, List<OrderItem> orderItemsList, Client client) {
        this.timeWaitingOrder = timeWaitingOrder;
        this.dateCreated = dateCreated;
        this.status = status;
        this.totalPrice = totalPrice;
        this.client = client;
        this.orderItemsList = orderItemsList;
    }
}
