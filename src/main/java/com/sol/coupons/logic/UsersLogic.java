package com.sol.coupons.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sol.coupons.dal.IUsersDal;
import com.sol.coupons.dto.Customer;
import com.sol.coupons.dto.LoginData;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.dto.User;
import com.sol.coupons.entities.CustomerEntity;
import com.sol.coupons.entities.UserEntity;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.utils.JWTUtils;
import com.sol.coupons.utils.StatisticsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import static com.sol.coupons.utils.PermissionsUtils.isAdmin;
import static com.sol.coupons.utils.PermissionsUtils.isCustomer;

@Service
public class UsersLogic {

    private IUsersDal usersDal;

    @Autowired
    public UsersLogic(IUsersDal usersDal) {
        this.usersDal = usersDal;
    }

    public void createUser(SuccessfulLoginData successfulLoginData, User user) throws ServerException {
        if (isAdmin(user.getUserType()) && !isAdmin(successfulLoginData.getUserType())) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "Only admins can create admin users");
        }
        if (usersDal.isUsernameExists(user.getUsername())) {
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "Username already exist");
        }
        validateUser(user);
        UserEntity userEntity = convertUsertoUserEntity(user);
        usersDal.save(userEntity);
    }

    private UserEntity convertUsertoUserEntity(User user) {
        Integer companyId = null;
        if (user.getCompanyId() != null) {
            companyId = user.getCompanyId();
        }
        UserEntity userEntity = new UserEntity(user.getId(), user.getUsername(), user.getPassword(), user.getUserType(), companyId);
        return userEntity;
    }

    public String login(LoginData loginData) throws ServerException, JsonProcessingException {
        UserEntity userEntity = usersDal.login(loginData.getUsername(), loginData.getPassword());
        if (userEntity == null) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED);
        }
        StatisticsUtils.sendStatistics(loginData.getUsername(), "login");
        Integer companyId;
        if (userEntity.getCompany() == null) {
            companyId = null;
        } else {
            companyId = userEntity.getCompany().getId();
        }
        SuccessfulLoginData successfulLoginData = new SuccessfulLoginData(userEntity.getId(), userEntity.getUserType(), companyId);

        String token = JWTUtils.createJWT(successfulLoginData);
        return token;
    }

    public void updateUser(SuccessfulLoginData successfulLoginData,User user) throws Exception {
        validateUser(user);
        UserEntity userEntity = this.usersDal.findById(successfulLoginData.getId()).get();
        if(userEntity == null){
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "USer not found");
        }
        if(!isAdmin(successfulLoginData.getUserType()) && successfulLoginData.getId() != userEntity.getId()){
            throw new ServerException(ErrorTypes.UNAUTHORIZED);
        }
        userEntity.setPassword(user.getPassword());
        usersDal.save(userEntity);
    }

    public User getUserById(int id) throws ServerException {
        UserEntity userEntity = usersDal.findById(id).get();
        if (userEntity == null) {
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "User not found: " + id);
        }
        User user = convertUserEntityToUser(userEntity);
        return user;
    }

    public User getUserByUserName(String username) throws ServerException {
        UserEntity userEntity = usersDal.getUserByUserName(username);
        if (userEntity == null) {
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "User not found: " + username);
        }
        User user = convertUserEntityToUser(userEntity);
        return user;
    }

    private User convertUserEntityToUser(UserEntity userEntity) {
        Integer companyId = null;
        if (userEntity.getCompany() != null) {
            companyId = userEntity.getCompany().getId();
        }
        User user = new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getUserType(), companyId);
        return user;
    }

    public List<User> getUsers(SuccessfulLoginData successfulLoginData) throws ServerException {
        if (!isAdmin(successfulLoginData.getUserType())) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "User is not an admin");
        }
        List<UserEntity> userEntities = (List<UserEntity>) usersDal.findAll();
        List<User> users = convertUserEntitesToUsers(userEntities);
        return users;
    }

    private List<User> convertUserEntitesToUsers(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            User user = convertUserEntityToUser(userEntity);
            users.add(user);
        }
        return users;
    }

    public void deleteUser(SuccessfulLoginData successfulLoginData, Integer id) throws ServerException {
        if (!isAdmin(successfulLoginData.getUserType())) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "User is not an admin");
        }
        UserEntity userEntity = usersDal.findById(id).get();
        if(userEntity == null ){
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST, "The requested ID was not found");
        }
        if(isCustomer(userEntity.getUserType())){
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "Must first delete associated customer");
        }
        usersDal.deleteById(id);
    }

    private void validateUser(User user) throws ServerException {
        if (user.getPassword().length() < 8) {
            throw new ServerException(ErrorTypes.INVALID_USER_PASSWORD, user.toString());
        }
        if (user.getPassword() == null) {
            throw new ServerException(ErrorTypes.INVALID_USER_PASSWORD, user.toString());
        }
        if (user.getUsername() == null) {
            throw new ServerException(ErrorTypes.INVALID_USER_NAME, user.toString());
        }
        if (user.getUserType() == "COMPANY" && user.getCompanyId() == null) {
            throw new ServerException(ErrorTypes.INVALID_COMPANY_ID, user.toString());
        }
    }
}
