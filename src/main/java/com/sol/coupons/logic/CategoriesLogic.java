package com.sol.coupons.logic;

import com.sol.coupons.dal.ICategoriesDal;
import com.sol.coupons.dal.ICouponsDal;
import com.sol.coupons.dto.Category;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.entities.CategoryEntity;
import com.sol.coupons.entities.UserEntity;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sol.coupons.utils.PermissionsUtils.isAdmin;

@Service
public class CategoriesLogic {
    private ICategoriesDal categoriesDal;
    @Autowired
    public CategoriesLogic(ICategoriesDal categoriesDal) {
        this.categoriesDal = categoriesDal;
    }

    public void createCategory(Category category) throws ServerException {
        validateCategory(category);
        CategoryEntity categoryEntity = convertCategoryToCategoryEntitiy(category);
        categoriesDal.save(categoryEntity);
    }

    private CategoryEntity convertCategoryToCategoryEntitiy(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity(category.getId(),category.getName());
        return categoryEntity;
    }
    public Category getCategory(int id) throws Exception {
        CategoryEntity categoryEntity = categoriesDal.findById(id).get();
        Category category = convertCategoryEntityToCategory(categoryEntity);
        return category;
    }

    private Category convertCategoryEntityToCategory(CategoryEntity categoryEntity) {
        Category category = new Category(categoryEntity.getId(),categoryEntity.getName());
        return category;
    }

    public List<Category> getCategories() throws Exception {
        List<CategoryEntity> categoryEntities = (List<CategoryEntity>) categoriesDal.findAll();
        List<Category> categories = convertCategoryEntitiesToCategories(categoryEntities);
        return categories;
    }

    private List<Category> convertCategoryEntitiesToCategories(List<CategoryEntity> categoryEntities) {
        List<Category> categories = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntities){
            Category category = new Category(categoryEntity.getId(),categoryEntity.getName());
            categories.add(category);
        }
        return categories;
    }

    public void updateCategory(Category category) throws ServerException {
        validateCategory(category);
        CategoryEntity categoryEntity = convertCategoryToCategoryEntitiy(category);
        categoriesDal.save(categoryEntity);
    }


    private void validateCategory(Category category) throws ServerException {
        if (category.getName() == null || category.getName().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_CATEGORY_NAME, category.toString());
        }
    }

    public void deleteCategory(SuccessfulLoginData successfulLoginData,int id) throws ServerException {
        if(!isAdmin(successfulLoginData.getUserType())){
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "User is not an admin");
        }
        CategoryEntity categoryEntity = categoriesDal.findById(id).get();
        if(categoryEntity == null){
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST);
        }
        categoriesDal.deleteById(id);
    }
}
