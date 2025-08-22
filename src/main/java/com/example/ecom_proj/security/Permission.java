package com.example.ecom_proj.security;

public enum Permission {
    VIEW_PRODUCTS("view_products"),
    CREATE_PRODUCTS("create_products"),
    UPDATE_PRODUCTS("update_products"),
    DELETE_PRODUCTS("delete_products"),
    VIEW_ORDERS("view_orders"),
    VIEW_ALL_ORDERS("view_all_orders"),
    ADD_ORDERS("add_orders"),
    UPDATE_ORDER_STATUS("update_order_status"),
    MANAGE_USERS("manage_users");

    private final String value;

    Permission(String value) {
        this.value = value;
    }

    public String getValue() {          // getter method
        return value;
    }
}