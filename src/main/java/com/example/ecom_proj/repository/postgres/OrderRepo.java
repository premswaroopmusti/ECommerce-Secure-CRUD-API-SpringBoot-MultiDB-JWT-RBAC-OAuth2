package com.example.ecom_proj.repository.postgres;

import com.example.ecom_proj.model.postgres.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders, Long> {

    List<Orders> findByUserId(Integer userId);

}
