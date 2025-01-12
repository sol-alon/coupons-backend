package com.sol.coupons.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sol.coupons.dto.LoginData;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.dto.User;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.logic.UsersLogic;
import com.sol.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersLogic usersLogic;
    private SuccessfulLoginData successfulLoginData;

    @Autowired
    public UsersController(UsersLogic usersLogic) {
        this.usersLogic = usersLogic;
    }

    //WORKS
    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData) throws ServerException, JsonProcessingException{
        return this.usersLogic.login(loginData);
    }

    //WORKS
    @GetMapping("/myuser")
    public User getUser(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        return this.usersLogic.getUserById(successfulLoginData.getId());
    }

    //WORKS
    @GetMapping()
    public List<User> getAllUsers(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        return this.usersLogic.getUsers(successfulLoginData);
    }

    //WORKS
    @PostMapping
    public void addUser(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.usersLogic.createUser(successfulLoginData, user);
    }

    //WORKS
    @PutMapping
    public void changeUserPassword(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.usersLogic.updateUser(successfulLoginData,user);
    }

    //WORKS - NOTE: customers must be deleted from customers logic
    @DeleteMapping("/{id}")
    public void deleteUser(@RequestHeader("Authorization") String token, @PathVariable int id) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.usersLogic.deleteUser(successfulLoginData, id);
    }

}
