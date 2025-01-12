package com.sol.coupons.logic;

import com.sol.coupons.dal.ICouponsDal;
import com.sol.coupons.dto.Coupon;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.entities.CouponEntity;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sol.coupons.utils.PermissionsUtils.isCompany;

@Service
public class CouponsLogic {

    private ICouponsDal couponsDal;

    @Autowired
    public CouponsLogic(ICouponsDal couponsDal) {
        this.couponsDal = couponsDal;
    }

    public void createCoupon(SuccessfulLoginData successfulLoginData, Coupon coupon) throws ServerException {
        if (!isCompany(successfulLoginData.getUserType())) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED);
        }
        validateCoupon(coupon);
        coupon.setCompanyId(successfulLoginData.getCompanyId());
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        couponsDal.save(couponEntity);
    }

    private CouponEntity convertCouponToCouponEntity(Coupon coupon) {
        CouponEntity couponEntity = new CouponEntity(coupon.getId(), coupon.getTitle(), coupon.getDescription(), coupon.getPrice(), coupon.getCompanyId(), coupon.getCategoryId(), coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getImageUrl());
        return couponEntity;
    }

    public void updateCoupon(SuccessfulLoginData successfulLoginData, Coupon coupon) throws ServerException {
        if (!isCompany(successfulLoginData.getUserType())) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED);
        }
        validateCoupon(coupon);
        CouponEntity couponEntity = new CouponEntity(coupon.getId(), coupon.getTitle(), coupon.getDescription(), coupon.getPrice(), coupon.getCompanyId(), coupon.getCategoryId(), coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getImageUrl());
        couponsDal.save(couponEntity);
    }

    public void deleteCoupon(SuccessfulLoginData successfulLoginData, int id) throws ServerException {
        if (!isCompany(successfulLoginData.getUserType())) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "Must be a company user to perform this action");
        }
        CouponEntity couponEntity = couponsDal.findById(id).get();
        if (couponEntity == null) {
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "Coupon not found");
        }
        if (couponEntity.getCompany().getId() != successfulLoginData.getCompanyId()) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "You cannot delete this coupon");
        }
        couponsDal.deleteById(id);
    }


    public List<Coupon> getCoupons() {
        List<CouponEntity> couponEntities = (List<CouponEntity>) couponsDal.findAll();
        List<Coupon> coupons = convertCouponEntitiesToCoupon(couponEntities);
        return coupons;
    }

    public Coupon getCouponById(int id) {
        CouponEntity couponEntity = couponsDal.findById(id).get();
        Coupon coupon = convertCouponEntityToCoupon(couponEntity);
        return coupon;
    }

    private List<Coupon> convertCouponEntitiesToCoupon(List<CouponEntity> couponEntities) {
        List<Coupon> coupons = new ArrayList<>();
        for (CouponEntity couponEntity : couponEntities) {
            Coupon coupon = new Coupon(couponEntity.getId(), couponEntity.getTitle(), couponEntity.getDescription(), couponEntity.getPrice(), couponEntity.getCompany().getId(), couponEntity.getCategory().getId(), couponEntity.getStartDate(), couponEntity.getEndDate(), couponEntity.getAmount(), couponEntity.getImageUrl());
            coupons.add(coupon);
        }
        return coupons;
    }

    public void deleteExpiredCoupons() throws ServerException {
       couponsDal.deleteExpiredCoupons(Date.valueOf(LocalDate.now()));
    }

    public void decreaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        CouponEntity couponEntity = couponsDal.findById(couponId).get();
        if (couponEntity == null) {
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST);
        }
        int updatedAmount = couponEntity.getAmount() - purchaseAmount;
        couponEntity.setAmount(updatedAmount);
        couponsDal.save(couponEntity);
    }

    private Coupon convertCouponEntityToCoupon(CouponEntity couponEntity) {
        Coupon coupon = new Coupon(couponEntity.getId(), couponEntity.getTitle(), couponEntity.getDescription(), couponEntity.getPrice(), couponEntity.getCompany().getId(), couponEntity.getCategory().getId(), couponEntity.getStartDate(), couponEntity.getEndDate(), couponEntity.getAmount(), couponEntity.getImageUrl());
        return coupon;
    }

    public void increaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        CouponEntity couponEntity = couponsDal.findById(couponId).get();
        if (couponEntity == null) {
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST);
        }
        int updatedAmount = couponEntity.getAmount() + purchaseAmount;
        couponEntity.setAmount(updatedAmount);
        couponsDal.save(couponEntity);
    }

    private void validateCoupon(Coupon coupon) throws ServerException {
        if (coupon.getAmount() < 1) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_AMOUNT);
        }
        if (coupon.getDescription() == null) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_DESCRIPTION);
        }
        if (coupon.getTitle() == null) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_TITLE);
        }
        if (coupon.getPrice() < 0.1) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_PRICE);
        }
        if (coupon.getStartDate().before(Date.valueOf(LocalDate.now()))) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_START_END);
        }
    }

}
