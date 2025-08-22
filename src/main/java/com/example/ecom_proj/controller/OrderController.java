package com.example.ecom_proj.controller;


import com.example.ecom_proj.model.h2.Product;
import com.example.ecom_proj.model.postgres.OrderAuditLog;
import com.example.ecom_proj.model.postgres.OrderStatus;
import com.example.ecom_proj.model.postgres.Orders;
import com.example.ecom_proj.service.OrderAuditLogService;
import com.example.ecom_proj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAuditLogService orderAuditLogService;

    // Customer: see only their own orders
    @GetMapping("/my")
    public ResponseEntity<List<Orders>> getMyOrders() {
        return ResponseEntity.ok(orderService.getOrdersForCurrentUser());
    }

    // Admin/Seller: view all orders
    @GetMapping
    @PreAuthorize("hasAuthority('view_all_orders')")
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    @PreAuthorize("hasAuthority('add_orders')")
    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody Orders orders){

        try{
            Orders o1 = orderService.addOrder(orders);
            return new ResponseEntity<>(o1, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAuthority('update_order_status')")
    @PatchMapping("/{order_id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable long order_id,
                                               @RequestBody Map<String, String> body ){

        try {
            String newStatus = body.get("status");
            Orders updated = orderService.updateOrderStatus(order_id, newStatus);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid status: " + body.get("status"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Get single order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable long orderId) {
        Orders order = orderService.getOrderById(orderId);
        return (order != null) ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

}
