package com.sol.coupons.dal;

import com.sol.coupons.entities.CustomerEntity;
import com.sol.coupons.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ICustomersDal extends CrudRepository<CustomerEntity, Integer> {
    @Query("SELECT c FROM CustomerEntity c WHERE c.user.id = :userId")
    CustomerEntity findByUserId(@Param("userId") int userId);

}
