package com.example.ecom_proj.repository.h2;


import com.example.ecom_proj.model.h2.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {



}
