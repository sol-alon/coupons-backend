package com.sol.coupons.controllers;

import com.sol.coupons.dto.Category;
import com.sol.coupons.dto.Company;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.logic.CategoriesLogic;
import com.sol.coupons.logic.CompaniesLogic;
import com.sol.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {
    private CompaniesLogic companiesLogic;
    private SuccessfulLoginData successfulLoginData;
    @Autowired
    public CompaniesController(CompaniesLogic companiesLogic) {
        this.companiesLogic = companiesLogic;
    }


    // WORKS
    @GetMapping("/mycompany")
    public Company getCompany(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        return this.companiesLogic.getCompany(successfulLoginData.getCompanyId());
    }

    //WORKS
    @GetMapping()
    public List<Company> getAllCompanies(@RequestHeader("Authorization") String token ) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        return this.companiesLogic.getCompanies(successfulLoginData);
    }

    //WORKS
    @PostMapping
    public void addCompany(@RequestHeader("Authorization") String token ,@RequestBody Company company) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.companiesLogic.createCompany(successfulLoginData,company);
    }

    //WORKS
    @PutMapping
    public void updateCompany(@RequestHeader("Authorization") String token ,@RequestBody Company company) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.companiesLogic.updateCompany(successfulLoginData,company);
    }

    //WORKS
    @DeleteMapping("/{id}")
    public void deleteCompany(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.companiesLogic.deleteCompany(successfulLoginData,id);
    }

}


