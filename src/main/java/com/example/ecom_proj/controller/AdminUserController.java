package com.example.ecom_proj.controller;

import com.example.ecom_proj.model.postgres.Users;
import com.example.ecom_proj.security.Role;
import com.example.ecom_proj.service.AuditLogService;
import com.example.ecom_proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuditLogService auditLogService;

    @PreAuthorize("hasAuthority('manage_users')")
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody CreateUserRequest req) {
        Users u = new Users();
        u.setUsername(req.username());
        u.setPassword(req.password()); // will be encoded in service
        u.setRole(Role.valueOf(req.role())); // "CUSTOMER|SELLER|ADMIN"
        Users created = userService.admin_register(u);
        // Log the admin's action. product_id = null for user mgmt.
        auditLogService.logCurrentUser("CREATE_USER");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    public record CreateUserRequest(String username, String password, String role)
    {

    }

}



