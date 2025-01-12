package com.sol.coupons.controllers;

import com.sol.coupons.dto.Category;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.logic.CategoriesLogic;
import com.sol.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private CategoriesLogic categoriesLogic;
    private SuccessfulLoginData successfulLoginData;

    @Autowired
    public CategoriesController(CategoriesLogic categoriesLogic) {
        this.categoriesLogic = categoriesLogic;
    }


    //WORKS
    @GetMapping()
    public List<Category> getAllCategories() throws Exception {
        return this.categoriesLogic.getCategories();
    }

    //WORKS
    @PostMapping
    public void addCategory(@RequestHeader("Authorization") String token, @RequestBody Category category) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        if(!successfulLoginData.getUserType().equals("ADMIN")){
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "User is not an admin");
        }
        this.categoriesLogic.createCategory(category);
    }

    //WORKS
    @PutMapping
    public void updateCategory(@RequestHeader("Authorization") String token, @RequestBody Category category) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        if(!successfulLoginData.getUserType().equals("ADMIN")){
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "User is not an admin");
        }
        this.categoriesLogic.updateCategory(category);
    }

    //WORKS
    @DeleteMapping("/{id}")
    public void deleteCategory(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.categoriesLogic.deleteCategory(successfulLoginData,id);
    }

}
