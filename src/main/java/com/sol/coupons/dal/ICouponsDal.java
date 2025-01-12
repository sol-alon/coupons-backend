package com.sol.coupons.dal;

import com.sol.coupons.entities.CouponEntity;
import com.sol.coupons.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ICouponsDal extends CrudRepository<CouponEntity, Integer> {
//    @Query("DELETE FROM CouponEntity c WHERE < :currentDate")
//    void deleteExpiredCoupons(@Param("currentDate") Date currentDate);

    @Query("SELECT c FROM CouponEntity c WHERE c.endDate< :currentDate")
    void deleteExpiredCoupons(@Param("currentDate") Date currentDate);
}


