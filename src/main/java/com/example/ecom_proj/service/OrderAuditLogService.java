package com.example.ecom_proj.service;

import com.example.ecom_proj.model.postgres.AuditLog;
import com.example.ecom_proj.model.postgres.OrderAuditLog;
import com.example.ecom_proj.repository.postgres.OrderAuditLogRepository;
import com.example.ecom_proj.repository.postgres.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAuditLogService {

        @Autowired
        private OrderAuditLogRepository orderAuditLogRepository;

        @Autowired
        private AuditLogService auditLogService;

    public void logCurrentUser(String action, Long orderId){

        Integer userId = auditLogService.resolveCurrentUserId();
        orderAuditLogRepository.save(new OrderAuditLog(userId, action, orderId));
    }

    /**
     * Logs an action on behalf of a specific user id (e.g., system actions).
     */

    public void log(Integer userId, String action, Long orderId){

        orderAuditLogRepository.save(new OrderAuditLog(userId, action, orderId));

    }

}
