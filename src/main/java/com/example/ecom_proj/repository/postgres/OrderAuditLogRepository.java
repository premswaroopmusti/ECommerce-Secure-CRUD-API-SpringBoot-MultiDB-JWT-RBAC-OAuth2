package com.example.ecom_proj.repository.postgres;

import com.example.ecom_proj.model.postgres.OrderAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAuditLogRepository extends JpaRepository<OrderAuditLog, Long> {
}
