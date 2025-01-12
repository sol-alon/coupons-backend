package com.sol.coupons.dto;

import java.sql.Date;

public class Coupon {
    private int id;
    private String title;
    private String description;
    private float price;
    private int companyId;
    private int categoryId;
    private Date startDate;
    private Date endDate;
    private int amount;
    private String imageUrl;

    public Coupon() {
    }

    public Coupon(String title, String description, float price, int companyId, int categoryId, Date startDate, Date endDate, int amount, String imageUrl) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    public Coupon(int id, String title, String description, float price, int companyId, int categoryId, Date startDate, Date endDate, int amount, String imageUrl) {
        this(title, description, price, companyId, categoryId, startDate, endDate, amount, imageUrl);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
