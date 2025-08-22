package com.example.ecom_proj.model.postgres;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "Audit_Logs")
@NoArgsConstructor
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Actor user id (from Postgres Users table). May be null if unknown (e.g., OAuth2 principal not in Users).
    @Column(name = "user_id")
    private Integer userId;

    // e.g., CREATE_PRODUCT, UPDATE_PRODUCT, DELETE_PRODUCT, CREATE_USER
    @Column(nullable = false)
    private String action;

    // Book id from H2 (no FK across DBs)
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    public AuditLog(Integer userId, String action, int productId){

            this.userId = userId;
            this.action = action;
            this.productId = productId;
            this.timestamp = Instant.now();

    }

    public AuditLog(Integer userId, String action){

        this.userId = userId;
        this.action = action;
        this.timestamp = Instant.now();

    }

}
