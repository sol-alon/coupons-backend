package com.sol.coupons.dto;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String phone;
    private Integer amountOfKids;

    public Customer(int id, String name, String address, String phone, int amountOfKids) {
        this(name, address, phone, amountOfKids);
        this.id = id;
    }

    public Customer(String name, String address, String phone, int amountOfKids) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.amountOfKids = amountOfKids;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(Integer amountOfKids) {
        this.amountOfKids = amountOfKids;
    }
}
