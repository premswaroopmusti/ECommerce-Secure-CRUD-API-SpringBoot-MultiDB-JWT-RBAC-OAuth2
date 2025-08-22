package com.example.ecom_proj.repository.postgres;

import com.example.ecom_proj.model.postgres.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);
}
