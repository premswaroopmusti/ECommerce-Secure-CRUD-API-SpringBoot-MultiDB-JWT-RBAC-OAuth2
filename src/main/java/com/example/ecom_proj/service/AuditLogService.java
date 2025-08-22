package com.example.ecom_proj.service;

import com.example.ecom_proj.model.postgres.AuditLog;
import com.example.ecom_proj.model.postgres.Users;
import com.example.ecom_proj.repository.postgres.AuditLogRepository;
import com.example.ecom_proj.repository.postgres.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

            @Autowired
            private AuditLogRepository auditRepo;

            @Autowired
            private UserRepository userRepo;

            /**
             * Logs an action performed by the currently authenticated principal.
             * Attempts to resolve principal to Users.id; if not found, stores user_id = null.
            */

            public void logCurrentUser(String action, Integer productId){

                    Integer userId = resolveCurrentUserId();
                    auditRepo.save(new AuditLog(userId, action, productId));
            }

            public void logCurrentUser(String action){

                Integer userId = resolveCurrentUserId();
                auditRepo.save(new AuditLog(userId, action));
            }

            /**
             * Logs an action on behalf of a specific user id (e.g., system actions).
             */

            public void log(Integer userId, String action, int productId){

                    auditRepo.save(new AuditLog(userId, action, productId));

            }

            public Integer resolveCurrentUserId(){

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if(auth == null){
                    return null;
                }
                String username = auth.getName();
                if(username == null){
                    return null;
                }
                Users u = userRepo.findByUsername(username);
                return (u != null) ? u.getId() : null;
            }
}
