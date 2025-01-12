package com.sol.coupons.dal;

import com.sol.coupons.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface ICategoriesDal extends CrudRepository<CategoryEntity, Integer> {

}
