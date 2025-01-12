package com.sol.coupons.controllers;

import com.sol.coupons.dto.Customer;
import com.sol.coupons.dto.FullCustomerUser;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.dto.User;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.logic.CustomersLogic;
import com.sol.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sol.coupons.utils.PermissionsUtils.isAdmin;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    private CustomersLogic customersLogic;
    private SuccessfulLoginData successfulLoginData;

    @Autowired
    public CustomersController(CustomersLogic customersLogic) {
        this.customersLogic = customersLogic;
    }

    //WORKS
    @GetMapping("/mycustomer")
    public Customer getCustomer(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        return this.customersLogic.getCustomerByUserId(successfulLoginData.getId());
    }

    //WORKS
    @GetMapping()
    public List<Customer> getAllCustomers(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        return this.customersLogic.getCustomers(successfulLoginData);
    }

    //WORKS - need to add Try>Catch??
    @PostMapping
    public void addCustomer(@RequestBody FullCustomerUser fullCustomerUser) throws ServerException {
        Customer customer = fullCustomerUser.getCustomer();
        User user = fullCustomerUser.getUser();
        this.customersLogic.createCustomer(customer, user);
    }

    //WORKS
    @PutMapping
    public void updateCustomer(@RequestHeader("Authorization") String token, @RequestBody Customer customer) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.customersLogic.updateCustomer(successfulLoginData, customer);
    }

    //WORKS
    @DeleteMapping("/{id}")
    public void deleteCustomer(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.customersLogic.deleteCustomer(successfulLoginData);
    }
}
