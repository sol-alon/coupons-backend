package com.sol.coupons.dal;

import com.sol.coupons.entities.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

public interface ICompaniesDal extends CrudRepository<CompanyEntity, Integer> {

}
