package com.sol.coupons.dal;

import com.sol.coupons.entities.CustomerEntity;
import com.sol.coupons.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPurchasesDal extends CrudRepository<PurchaseEntity, Integer> {
    @Query("SELECT p FROM PurchaseEntity p WHERE p.customer.user.id = :userId")
    List<PurchaseEntity> findByUserId(@Param("userId") int userId);

    @Query("SELECT p FROM PurchaseEntity p WHERE p.coupon.company.id = :companyId")
    List<PurchaseEntity> findByCompanyId(@Param("companyId") int companyId);
}






