package com.sol.coupons.dto;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String userType;
    private Integer companyId;

    public User(String username, String password, String userType){
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.companyId = null;
    }

    public User(String username, String password, String userType, Integer companyId) {
        this(username, password, userType);
        this.companyId = companyId;
    }

    public User(Integer id, String username, String password, String userType, Integer companyId){
        this(username,password,userType,companyId);
        this.id = id;
    }
    public User(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
