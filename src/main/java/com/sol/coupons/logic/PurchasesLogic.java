package com.sol.coupons.logic;

import com.sol.coupons.dal.ICustomersDal;
import com.sol.coupons.dal.IPurchasesDal;
import com.sol.coupons.dto.Coupon;
import com.sol.coupons.dto.Purchase;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.entities.PurchaseEntity;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.sol.coupons.utils.PermissionsUtils.isAdmin;
import static com.sol.coupons.utils.PermissionsUtils.isCompany;

@Service
public class PurchasesLogic {
    private IPurchasesDal purchasesDal;
    private CouponsLogic couponsLogic;
    private CustomersLogic customersLogic;

    @Autowired
    public PurchasesLogic(IPurchasesDal purchasesDal, CouponsLogic couponsLogic, CustomersLogic customersLogic) {
        this.purchasesDal = purchasesDal;
        this.couponsLogic = couponsLogic;
        this.customersLogic = customersLogic;
    }


    public void createPurchase(SuccessfulLoginData successfulLoginData, Purchase purchase) throws ServerException {
        if (!successfulLoginData.getUserType().equals("CUSTOMER")) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "Only site customers can purchase coupons");
        }
        purchase.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
        purchase.setCustomerId(customersLogic.getCustomerByUserId(successfulLoginData.getId()).getId());
        validatePurchases(purchase);
        couponsLogic.decreaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        PurchaseEntity purchaseEntity = convertPurchaseToPurchaseEntity(purchase);
        purchasesDal.save(purchaseEntity);
    }

    private PurchaseEntity convertPurchaseToPurchaseEntity(Purchase purchase) {
        PurchaseEntity purchaseEntity = new PurchaseEntity(purchase.getId(), purchase.getCustomerId(), purchase.getCouponId(), purchase.getAmount(), purchase.getTimeStamp());
        return purchaseEntity;
    }

    public void deletePurchase(int id) throws ServerException {
        purchasesDal.deleteById(id);
    }

    public List<Purchase> getPurchases(SuccessfulLoginData successfulLoginData) throws ServerException {
        List<Purchase> purchases = new ArrayList<>();

        if (isAdmin(successfulLoginData.getUserType())) {
            List<PurchaseEntity> purchaseEntities = (List<PurchaseEntity>) purchasesDal.findAll();
            purchases = convertPurchaseEntitiesToPurchases(purchaseEntities);
            return purchases;
        }
        if (isCompany(successfulLoginData.getUserType())) {
            List<PurchaseEntity> purchaseEntities = (List<PurchaseEntity>) purchasesDal.findByCompanyId(successfulLoginData.getCompanyId());
            purchases = convertPurchaseEntitiesToPurchases(purchaseEntities);
            return purchases;
        }
        List<PurchaseEntity> purchaseEntities = (List<PurchaseEntity>) purchasesDal.findByUserId(successfulLoginData.getId());
        purchases = convertPurchaseEntitiesToPurchases(purchaseEntities);
        return purchases;
    }

    private List<Purchase> convertPurchaseEntitiesToPurchases(List<PurchaseEntity> purchaseEntities) {
        List<Purchase> purchases = new ArrayList<>();
        for (PurchaseEntity purchaseEntity : purchaseEntities) {
            Purchase purchase = new Purchase(purchaseEntity.getId(), purchaseEntity.getCustomer().getId(), purchaseEntity.getCoupon().getId(), purchaseEntity.getAmount(), purchaseEntity.getTimeStamp());
            purchases.add(purchase);
        }
        return purchases;
    }

    public Purchase getPurchaseById(int id) throws ServerException {
        PurchaseEntity purchaseEntity = purchasesDal.findById(id).get();
        if (purchaseEntity == null) {
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST, id + "");
        }
        Purchase purchase = new Purchase(purchaseEntity.getId(), purchaseEntity.getCustomer().getId(), purchaseEntity.getCoupon().getId(), purchaseEntity.getAmount(), purchaseEntity.getTimeStamp());
        return purchase;
    }

    private void validatePurchases(Purchase purchase) throws ServerException {
        Coupon coupon = couponsLogic.getCouponById(purchase.getCouponId());

        if (coupon == null) {
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST, "ID: " + coupon.getId());
        }

        if (purchase.getAmount() < 1) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_AMOUNT, "Coupon is sold out " + coupon.getId());
        }

        if (coupon.getAmount() < purchase.getAmount()) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_AMOUNT, "Not enough coupons");
        }

        if (purchase.getTimeStamp().after(coupon.getEndDate())) {
            throw new ServerException(ErrorTypes.INVALID_DATE, "Coupon is no longer available" + coupon.getTitle());
        }
    }

    public void deleteExpiredCouponsPurchases(int couponId) throws ServerException {
        purchasesDal.deleteById(couponId);
    }
}
