package com.example.ecom_proj.security;

import java.util.Set;

import static com.example.ecom_proj.security.Permission.*;


public enum Role {
    CUSTOMER(Set.of(VIEW_PRODUCTS, VIEW_ORDERS, ADD_ORDERS)),
    SELLER(Set.of(VIEW_PRODUCTS,  CREATE_PRODUCTS, UPDATE_PRODUCTS, DELETE_PRODUCTS,VIEW_ORDERS, UPDATE_ORDER_STATUS,VIEW_ALL_ORDERS)),
    ADMINISTRATOR(Set.of(VIEW_PRODUCTS,CREATE_PRODUCTS, UPDATE_PRODUCTS, DELETE_PRODUCTS, MANAGE_USERS, VIEW_ORDERS, UPDATE_ORDER_STATUS,VIEW_ALL_ORDERS));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {     // getter method
        return permissions;
    }
}
