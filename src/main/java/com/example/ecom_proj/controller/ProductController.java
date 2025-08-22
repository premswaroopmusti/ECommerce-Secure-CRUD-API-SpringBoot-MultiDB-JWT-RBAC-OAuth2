package com.example.ecom_proj.controller;
import com.example.ecom_proj.model.h2.Product;
import com.example.ecom_proj.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ProductController {

        @Autowired
        private ProductService service;


        @GetMapping("/products")
        public ResponseEntity<List<Product>> getAllProducts(){
                return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
        }

        @PreAuthorize("hasAuthority('view_products')")
        @GetMapping("/product/{id}")
        public ResponseEntity<Product> getProductById(@PathVariable int id){
                Product prod = service.getProductById(id);

                if(prod != null){
                        return new ResponseEntity<>(prod, HttpStatus.OK);
                }
                else{
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }

        @PreAuthorize("hasAuthority('create_products')")
        @PostMapping("/products")
        public ResponseEntity<?> addProduct(@RequestBody Product prod){

                try{
                        Product product1 = service.addProduct(prod);
                        return new ResponseEntity<>(product1, HttpStatus.CREATED);
                   }
                catch(Exception e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @PreAuthorize("hasAuthority('update_products')")
        @PutMapping("/product")
        public ResponseEntity<?> updateProduct(@RequestBody Product prod){

                try {
                        Product prod1 = service.updateProduct(prod);
                        return new ResponseEntity<>(prod1, HttpStatus.OK);
                }
                catch(Exception e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @PreAuthorize("hasAuthority('delete_products')")
        @DeleteMapping("/product/{id}")
        public ResponseEntity<?> deleteProduct(@PathVariable int id) {
                        Product prod1 = service.getProductById(id);
                if(prod1 != null) {
                        service.deleteProduct(id);
                        return new ResponseEntity<>("Deleted", HttpStatus.OK);
                } else {
                        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
                }
        }

        @GetMapping("/csrf-token")
        public CsrfToken getCSRFToken(HttpServletRequest request){
                return (CsrfToken) request.getAttribute("_csrf");
        }

}