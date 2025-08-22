package com.example.ecom_proj.model.postgres;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_id;

    private String product_name;
    private int quantity;
    private double unit_price;
    private double total_price;

    @Column(name = "user_id", nullable = false)
    private Integer userId;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status  = OrderStatus.PENDING;




}
