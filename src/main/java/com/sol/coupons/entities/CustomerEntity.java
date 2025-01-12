package com.sol.coupons.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", nullable = false , length = 45)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "amount_of_kids")
    private Integer amountOfKids;

    @Column(name = "phone", unique = true, nullable = false , length = 10)
    private String phone;

    @OneToOne(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    private UserEntity user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<PurchaseEntity> purchases;

    public CustomerEntity() {
    }

    public CustomerEntity(String name, String address, Integer amountOfKids, String phone) {
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user = new UserEntity();
        this.user.setId(null);
        }

    public CustomerEntity(int id, String name, String address, Integer amountOfKids, String phone) {
        this(name, address, amountOfKids, phone);
        this.id = id;
        this.user = new UserEntity();
        this.user.setId(id);
    }


    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public Integer getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(Integer amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
