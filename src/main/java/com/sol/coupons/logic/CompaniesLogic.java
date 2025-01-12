package com.sol.coupons.logic;

import com.sol.coupons.dal.ICompaniesDal;
import com.sol.coupons.dto.Company;
import com.sol.coupons.dto.SuccessfulLoginData;
import com.sol.coupons.entities.CompanyEntity;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sol.coupons.utils.PermissionsUtils.isAdmin;

@Service
public class CompaniesLogic {
    private ICompaniesDal companiesDal;

    @Autowired
    public CompaniesLogic(ICompaniesDal companiesDal) {
        this.companiesDal = companiesDal;
    }


    public void createCompany(SuccessfulLoginData successfulLoginData,Company company) throws ServerException {
        if(!isAdmin(successfulLoginData.getUserType())){
            throw new ServerException(ErrorTypes.UNAUTHORIZED);
        }
        validateCompany(company);
        CompanyEntity companyEntity = convertCompanyToCompanyEntity(company);
        companiesDal.save(companyEntity);
    }

    private CompanyEntity convertCompanyToCompanyEntity(Company company) {
        CompanyEntity companyEntity = new CompanyEntity(company.getId(), company.getName(), company.getAddress(), company.getPhone());
        return companyEntity;
    }

    public void updateCompany(SuccessfulLoginData successfulLoginData,Company company) throws ServerException {
        if(!isAdmin(successfulLoginData.getUserType())){
            throw new ServerException(ErrorTypes.UNAUTHORIZED);
        }
        validateCompany(company);
        CompanyEntity companyEntity = this.companiesDal.findById(company.getId()).get();
        if (companyEntity == null) {
            throw new ServerException(ErrorTypes.INVALID_COMPANY_ID, company.toString());
        }
        companyEntity.setAddress(company.getAddress());
        companyEntity.setName(company.getName());
        companyEntity.setPhone(company.getPhone());
        companiesDal.save(companyEntity);
    }

    public Company getCompany(int id) throws ServerException {
        CompanyEntity companyEntity = companiesDal.findById(id).get();
        if(companyEntity == null){
            throw new ServerException(ErrorTypes.INVALID_COMPANY_ID);
        }
        Company company = convertCompanyEntityToCompany(companyEntity);
        return company;
    }

    private Company convertCompanyEntityToCompany(CompanyEntity companyEntity) {
        Company company = new Company(companyEntity.getId(),companyEntity.getName(),companyEntity.getAddress(),companyEntity.getPhone());
        return company;
    }

    public List<Company> getCompanies(SuccessfulLoginData successfulLoginData) throws ServerException {
        if(!isAdmin(successfulLoginData.getUserType())){
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "You don't have sufficient permissions");
        }
        List<CompanyEntity> companyEntities = (List<CompanyEntity>) companiesDal.findAll();
        List<Company> companies = convertCompanyEntitiesToCompany(companyEntities);
        return companies;
    }

    private List<Company> convertCompanyEntitiesToCompany(List<CompanyEntity> companyEntities) {
        List<Company> companies = new ArrayList<>();
        for(CompanyEntity companyEntity : companyEntities){
            Company company = new Company(companyEntity.getId(),companyEntity.getName(),companyEntity.getAddress(),companyEntity.getPhone());
            companies.add(company);
        }
        return companies;
    }


    public void deleteCompany(SuccessfulLoginData successfulLoginData,int id) throws ServerException {
        if(!isAdmin(successfulLoginData.getUserType())){
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "Must have admin privileges to perform this action");
        }
        CompanyEntity companyEntity = companiesDal.findById(id).get();
        if(companyEntity == null){
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST, "Company not found");
        }
        companiesDal.deleteById(id);
    }

    private void validateCompany(Company company) throws ServerException {
        if (company.getName() == null || company.getName().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_NAME, company.toString());
        }
        if (company.getPhone() != null && (company.getPhone().length() < 10)) {
            throw new ServerException(ErrorTypes.INVALID_PHONE, company.toString());
        }
        if (company.getAddress() != null && company.getAddress().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_ADDRESS, company.toString());
        }

    }
}
