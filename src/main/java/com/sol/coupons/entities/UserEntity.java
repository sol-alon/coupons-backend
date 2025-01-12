package com.sol.coupons.entities;
import javax.persistence.*;
    @Entity
    @Table(name = "users")
    public class UserEntity {

        @Id
        @GeneratedValue
        private Integer id;

        @Column(name = "username", unique = true, nullable = false, length = 45)
        private String username;

        @Column(name = "password", nullable = false, length = 45)
        private String password;

        @Column(name = "user_type", nullable = false)
        private String userType;

        @ManyToOne()
        private CompanyEntity company;

        public UserEntity() {
        }

        public UserEntity(String username, String password, String userType, Integer companyId) {
            this.username = username;
            this.password = password;
            this.userType = userType;
            if(companyId != null) {
                this.company = new CompanyEntity();
                this.company.setId(companyId);
            }
        }

        public UserEntity(Integer id, String username, String password, String userType, Integer companyId) {
            this(username, password, userType, companyId);
            this.id = id;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserType() {
            return userType;
        }

        public CompanyEntity getCompany() {
            return company;
        }

        public void setCompany(CompanyEntity company) {
            this.company = company;
        }
    }


