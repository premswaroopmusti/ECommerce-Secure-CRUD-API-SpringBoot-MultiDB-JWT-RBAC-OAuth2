package com.example.ecom_proj.service;
import com.example.ecom_proj.model.h2.Product;
import com.example.ecom_proj.repository.h2.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @Autowired
    private AuditLogService auditLogService;

    public List<Product> getAllProducts(){
            return repo.findAll();
    }

    public Product getProductById(int id){
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product prod){
        Product saved =  repo.save(prod);
        auditLogService.logCurrentUser("CREATE_PRODUCT", saved.getId());
        return saved;
    }

    public Product updateProduct(Product prod){
        Product saved = repo.save(prod);
        auditLogService.logCurrentUser("UPDATE_PRODUCT", saved.getId());
        return saved;
    }

    public void deleteProduct(int id)   {
            repo.deleteById(id);
            auditLogService.logCurrentUser("DELETE_PRODUCT", id);
    }

}
