package com.sol.coupons.controllers;

import com.sol.coupons.dto.Coupon;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.logic.CouponsLogic;
import com.sol.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {
    private CouponsLogic couponsLogic;
    private SuccessfulLoginData successfulLoginData;
    @Autowired
    public CouponsController(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    //in Whitelist - how to do without id??
    @GetMapping("/coupon")
    public Coupon getCoupon(@PathVariable("id") int id) {
        return this.couponsLogic.getCouponById(id);
    }

    //WORKS
    @GetMapping()
    public List<Coupon> getAllCoupons() {
        return this.couponsLogic.getCoupons();
    }

    //WORKS
    @PostMapping
    public void addCoupon(@RequestHeader("Authorization") String token,@RequestBody Coupon coupon) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.couponsLogic.createCoupon(successfulLoginData,coupon);
    }

    //WORKS
    @PutMapping
    public void updateCoupon(@RequestHeader("Authorization") String token,@RequestBody Coupon coupon) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.couponsLogic.updateCoupon(successfulLoginData,coupon);
    }

    //WORKS
    @DeleteMapping("/{id}")
    public void deleteCoupon(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        successfulLoginData = JWTUtils.decodeJWT(token);
        this.couponsLogic.deleteCoupon(successfulLoginData,id);
    }

//    @DeleteMapping
//    public void deleteExpiredCouponsTest() throws ServerException {
//        this.couponsLogic.deleteExpiredCoupons();
//    }

}
