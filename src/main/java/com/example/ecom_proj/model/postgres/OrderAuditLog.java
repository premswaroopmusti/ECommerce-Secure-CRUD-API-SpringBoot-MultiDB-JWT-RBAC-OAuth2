package com.example.ecom_proj.model.postgres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "Order_Audit_Logs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // e.g., CREATE_ORDER, UPDATE_ORDER_STATUS, CANCEL_ORDER
    @Column(nullable = false)
    private String action;

    @Column(nullable = false, updatable = false)
    private Instant timestamp;

    @Column(name = "order_id")
    private Long orderId;


    @PrePersist
    public void prePersist() {
        this.timestamp = Instant.now();
    }

    // convenience constructor
    public OrderAuditLog(Integer userId, String action, Long orderId) {
        this.userId = userId;
        this.action = action;
        this.orderId = orderId;
        this.timestamp = Instant.now();

    }

}
