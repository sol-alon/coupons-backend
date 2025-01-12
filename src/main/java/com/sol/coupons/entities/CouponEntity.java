package com.sol.coupons.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "coupons")
public class CouponEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    private CompanyEntity company;
    @ManyToOne
    private CategoryEntity category;

    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "amount", nullable = false)
    private int amount;
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchases;

    public CouponEntity(){};
    public CouponEntity(String title, String description, float price, int companyId, int categoryId, Date startDate, Date endDate, int amount, String imageUrl) {
        this.title = title;
        this.description = description;
        this.price = price;

        this.company = new CompanyEntity();
        this.company.setId(companyId);

        this.category = new CategoryEntity();
        this.category.setId(categoryId);

        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    public CouponEntity(int id, String title, String description, float price, int companyId, int categoryId, Date startDate, Date endDate, int amount, String imageUrl) {
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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
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
