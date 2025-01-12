package com.sol.coupons.dal;

import com.sol.coupons.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUsersDal extends CrudRepository<UserEntity, Integer> {

    @Query("SELECT count(u.id) > 0 FROM UserEntity u WHERE username= :username")
    boolean isUsernameExists(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u WHERE u.username= :username AND u.password= :password")
    UserEntity login(@Param("username") String username, @Param("password") String password);

    @Query("SELECT u FROM UserEntity u WHERE username= :username")
    UserEntity getUserByUserName(@Param("username") String username);
}


