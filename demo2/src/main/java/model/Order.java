package model;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int userId;
    private int dishId;
    private Timestamp orderDateTime;
    private OrderStatus status;

    // Empty constructor
    public Order() {
    }

    // Constructor with all properties
    public Order(int orderId, int userId, int dishId, Timestamp orderDateTime, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.dishId = dishId;
        this.orderDateTime = orderDateTime;
        this.status = status != null ? status : OrderStatus.READY;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public Timestamp getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Timestamp orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status != null ? status : OrderStatus.READY; // Ensure default status is 'READY' if null
    }
}
