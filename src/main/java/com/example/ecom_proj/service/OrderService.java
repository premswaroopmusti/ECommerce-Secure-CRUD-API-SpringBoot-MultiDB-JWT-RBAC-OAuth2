package com.example.ecom_proj.service;

import com.example.ecom_proj.model.h2.Product;
import com.example.ecom_proj.model.postgres.OrderStatus;
import com.example.ecom_proj.model.postgres.Orders;
import com.example.ecom_proj.repository.h2.ProductRepo;
import com.example.ecom_proj.repository.postgres.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo repo;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private OrderAuditLogService orderAuditLogService;

    // For normal customers: see their own orders
    public List<Orders> getOrdersForCurrentUser() {
        Integer currentUserId = auditLogService.resolveCurrentUserId();
        return repo.findByUserId(currentUserId);
    }

    // Only Admin/Seller can see ALL orders
    @PreAuthorize("hasAuthority('view_all_orders')")
    public List<Orders> getAllOrders() {
        return repo.findAll();
    }


    public Orders addOrder(Orders orders){

        Integer currentUserId = auditLogService.resolveCurrentUserId();
        orders.setUserId(currentUserId);

        Orders saved = repo.save(orders);
        orderAuditLogService.logCurrentUser("ADD_ORDERS", saved.getOrder_id());
        return saved;

    }

    public Orders updateOrderStatus(Long orderId, String newStatus){
        Orders existing = repo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Convert String → Enum once here
        OrderStatus statusEnum = OrderStatus.valueOf(newStatus.toUpperCase());
        existing.setStatus(statusEnum);

        Orders updated = repo.save(existing);
        orderAuditLogService.logCurrentUser("UPDATE_ORDER_STATUS", updated.getOrder_id());
        return updated;
    }


    public Orders getOrderById(long orderId) {
        return repo.findById(orderId).orElse(null);
    }
}
