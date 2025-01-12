package com.sol.coupons.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name", nullable = false)
    private  String name;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone", nullable = false)
    private String phone;
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CouponEntity> coupons;
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<UserEntity> users;


    public CompanyEntity(){
    }

    public CompanyEntity(String name, String address, String phone){
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public CompanyEntity(int id, String name, String address, String phone){
        this(name, address, phone);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
