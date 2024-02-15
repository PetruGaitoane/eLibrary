package com.example.demo.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class OrderInput {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]{2,}$")
    private String fullName;
    @NotNull
    private String fullAddress;
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    private String contactNumber;
    private List<OrderBookQuantity> orderBookQuantityList;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<OrderBookQuantity> getOrderBookQuantityList() {
        return orderBookQuantityList;
    }

    public void setOrderBookQuantityList(List<OrderBookQuantity> orderBookQuantityList) {
        this.orderBookQuantityList = orderBookQuantityList;
    }
}
