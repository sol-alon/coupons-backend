package com.sol.coupons.logic;

import com.sol.coupons.dal.ICustomersDal;
import com.sol.coupons.dto.Customer;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.dto.User;
import com.sol.coupons.entities.CustomerEntity;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.sol.coupons.utils.PermissionsUtils.isAdmin;
import static com.sol.coupons.utils.PermissionsUtils.isCustomer;

@Service
public class CustomersLogic {
    private ICustomersDal customersDal;
    private UsersLogic usersLogic;

    private SuccessfulLoginData successfulLoginData;

    @Autowired
    public CustomersLogic(ICustomersDal customersDal, UsersLogic usersLogic) {
        this.usersLogic = usersLogic;
        this.customersDal = customersDal;
    }

    @Transactional
    public void createCustomer(Customer customer, User user) throws ServerException {
        validateCustomer(customer);
        usersLogic.createUser(successfulLoginData, user);
        User newUser = usersLogic.getUserByUserName(user.getUsername());
        CustomerEntity customerEntity = convertCustomerToCustomerEntity(customer);
        customerEntity.getUser().setId(newUser.getId());
        customersDal.save(customerEntity);
    }

    private CustomerEntity convertCustomerToCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(customer.getName(), customer.getAddress(), customer.getAmountOfKids(), customer.getPhone());
        return customerEntity;
    }

    public void updateCustomer(SuccessfulLoginData successfulLoginData ,Customer customer) throws ServerException {
        validateCustomer(customer);
        CustomerEntity customerEntity = this.customersDal.findById(customer.getId()).get();
        if (customerEntity == null) {
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "Customer not found");
        }
        if(!isAdmin(successfulLoginData.getUserType()) && successfulLoginData.getId() != customerEntity.getUser().getId()){
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "You do not have permission to update this customer");
        }
        customerEntity.setAddress(customer.getAddress());
        customerEntity.setName(customer.getName());
        customerEntity.setPhone(customer.getPhone());
        customerEntity.setAmountOfKids(customer.getAmountOfKids());
        customersDal.save(customerEntity);
    }

    public void deleteCustomer(SuccessfulLoginData successfulLoginData) throws ServerException {
        if(!isCustomer(successfulLoginData.getUserType())){
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "You cannot perform this action");
        }
        CustomerEntity customerEntity = customersDal.findByUserId(successfulLoginData.getId());
        if(customerEntity == null){
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST,"Customer not found");
        }
        customersDal.deleteById(customerEntity.getId());
    }

    public List<Customer> getCustomers(SuccessfulLoginData successfulLoginData) throws ServerException {
        if(!isAdmin(successfulLoginData.getUserType())){
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "User is not an admin");
        }
        List<CustomerEntity> customerEntities = (List<CustomerEntity>) this.customersDal.findAll();
        List<Customer> customers = new ArrayList<>();
        for (CustomerEntity customerEntity : customerEntities) {
            Customer customer = convertCustomerEntityToCustomer(customerEntity);
            customers.add(customer);
        }
        return customers;
    }

    private Customer convertCustomerEntityToCustomer(CustomerEntity customerEntity) {
        Customer customer = new Customer(customerEntity.getId(), customerEntity.getName(), customerEntity.getAddress(), customerEntity.getPhone(), customerEntity.getAmountOfKids());
        return customer;
    }

    public Customer getCustomerById(int id) throws ServerException{
        CustomerEntity customerEntity = customersDal.findById(id).get();
        if(customerEntity == null){
            throw new ServerException(ErrorTypes.GENERAL_ERROR, "Customer not found: " + id);
        }
        Customer customer = convertCustomerEntityToCustomer(customerEntity);
        return customer;
    }

    public Customer getCustomerByUserId(int userId) throws  ServerException{
        CustomerEntity customerEntity = customersDal.findByUserId(userId);
        if(customerEntity == null){
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST);
        }
        Customer customer = convertCustomerEntityToCustomer(customerEntity);
        return customer;
    }

    private void validateCustomer(Customer customer) throws ServerException {

        if (customer.getName() == null || customer.getName().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_NAME, customer.toString());
        }
        if (customer.getPhone() != null && (customer.getPhone().length() < 9 || customer.getPhone().length() > 15)) {
            throw new ServerException(ErrorTypes.INVALID_PHONE, customer.toString());
        }
        if (customer.getAddress() != null && customer.getAddress().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_ADDRESS, customer.toString());
        }
    }
}
