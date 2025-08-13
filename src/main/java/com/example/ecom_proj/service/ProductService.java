package com.example.ecom_proj.service;
import com.example.ecom_proj.model.Product;
import com.example.ecom_proj.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts(){
            return repo.findAll();
    }

    public Product getProductById(int id){
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product prod){
        return repo.save(prod);
    }

    public Product updateProduct(Product prod){
        return repo.save(prod);
    }

    public void deleteProduct(int id)   {
            repo.deleteById(id);
    }

}
