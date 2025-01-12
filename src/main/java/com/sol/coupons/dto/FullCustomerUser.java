package com.sol.coupons.dto;

import com.sol.coupons.dto.Customer;
import com.sol.coupons.dto.User;

public class FullCustomerUser{
    private Customer customer;
    private User user;

    public FullCustomerUser(){};

    public FullCustomerUser(Customer customer, User user) {
        this.customer = customer;
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
