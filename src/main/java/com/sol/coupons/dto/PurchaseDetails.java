package com.sol.coupons.dto;

import java.sql.Timestamp;
import java.util.Date;

public class PurchaseDetails {
    private int id;
    private int couponId;
    private int amount;
    private Timestamp timestamp;
    private float totalPrice;
    private String title;
    private String description;
    private String imageURL;
    private Date endDate;

    public PurchaseDetails(int id, int couponId, int amount, Timestamp timestamp, float totalPrice, String title, String description, String imageURL , Date endDate) {
        this.id = id;
        this.couponId = couponId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.totalPrice = totalPrice;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }


    public int getCouponId() {
        return couponId;
    }

    public int getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "PurchaseDetails{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", totalPrice=" + totalPrice +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}

