package com.sol.coupons.controllers;

import com.sol.coupons.dto.Purchase;
import com.sol.coupons.dto.PurchaseDetails;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.logic.PurchasesDetailsLogic;
import com.sol.coupons.logic.PurchasesLogic;
import com.sol.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/purchases")
public class PurchasesController {
    private PurchasesLogic purchasesLogic;
    private PurchasesDetailsLogic purchasesDetailsLogic;
    private SuccessfulLoginData successfulLoginData;

    @Autowired
    public PurchasesController(PurchasesLogic purchasesLogic, PurchasesDetailsLogic purchasesDetailsLogic){
        this.purchasesLogic = purchasesLogic;
        this.purchasesDetailsLogic = purchasesDetailsLogic;
    }

    @GetMapping("/{id}")
    public PurchaseDetails getPurchaseDetails(@PathVariable("id") int id) throws ServerException {
        return this.purchasesDetailsLogic.getPurchaseDetailsByPurchaseId(id);
    }

    //WORKS
    @GetMapping()
    public List<Purchase> getAllPurchases(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchases(successfulLoginData);
    }

    //WORKS
    @PostMapping()
    public void addPurchase(@RequestHeader("Authorization") String token, @RequestBody Purchase purchase) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.purchasesLogic.createPurchase(successfulLoginData,purchase);
    }

}
