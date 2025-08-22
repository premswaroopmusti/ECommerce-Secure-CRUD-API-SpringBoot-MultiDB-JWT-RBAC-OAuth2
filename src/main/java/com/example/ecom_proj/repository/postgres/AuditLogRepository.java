package com.example.ecom_proj.repository.postgres;

import com.example.ecom_proj.model.postgres.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer>{



}
