package com.sol.coupons.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purchases")
public class PurchaseEntity {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne()
    private CustomerEntity customer;
    @ManyToOne
    private CouponEntity coupon;
    @Column(name = "amount",nullable = false)
    private int amount;
    @Column(name = "time_stamp",nullable = false)
    private Timestamp timeStamp;

    public PurchaseEntity(){};
    public PurchaseEntity(int customerId, int couponId, int amount, Timestamp timeStamp) {
        this.customer = new CustomerEntity();
        this.customer.setId(customerId);
        this.coupon = new CouponEntity();
        this.coupon.setId(couponId);
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public PurchaseEntity(int id, int customerId, int couponId, int amount, Timestamp timeStamp) {
        this(customerId, couponId, amount, timeStamp);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
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
