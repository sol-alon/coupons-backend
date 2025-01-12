package com.sol.coupons.dto;

import java.sql.Timestamp;

public class Purchase {
    private int id;
    private int customerId;
    private int couponId;
    private int amount;
    private Timestamp timeStamp;

    public Purchase(int id, int customerId, int couponId, int amount, Timestamp timeStamp) {
        this.id = id;
        this.customerId = customerId;
        this.couponId = couponId;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public Purchase(int customerId, int couponId, int amount, Timestamp timeStamp){
        this(0,customerId,couponId,amount,timeStamp);
    }

    public Purchase(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
