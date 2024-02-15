package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity

public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]{2,}$")
    private String orderUserName;

    @NotNull
    private String orderContactAddress;
    @NotNull
    private String orderContactNumber;
    private String orderStatus;

    private int orderQuantity;

    private double orderSgBookValue;

    private double orderValue;
    @OneToOne
    private Book book;


    public OrderDetail() {
    }

    public OrderDetail(String orderUserName, String orderContactAddress, String orderContactNumber, String orderStatus, int orderQuantity, double orderSgBookValue,double orderValue, Book book) {
        this.orderUserName = orderUserName;
        this.orderContactAddress = orderContactAddress;
        this.orderContactNumber = orderContactNumber;
        this.orderStatus = orderStatus;
        this.orderQuantity = orderQuantity;
        this.orderSgBookValue = orderSgBookValue;
        this.orderValue = orderValue;
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getOrderSgBookValue() {
        return orderSgBookValue;
    }

    public void setOrderSgBookValue(double orderSgBookValue) {
        this.orderSgBookValue = orderSgBookValue;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderContactAddress() {
        return orderContactAddress;
    }

    public void setOrderContactAddress(String orderContactAddress) {
        this.orderContactAddress = orderContactAddress;
    }

    public String getOrderContactNumber() {
        return orderContactNumber;
    }

    public void setOrderContactNumber(String orderContactNumber) {
        this.orderContactNumber = orderContactNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }
}
