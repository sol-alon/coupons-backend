package com.sol.coupons.logic;

import com.sol.coupons.dal.IPurchasesDal;
import com.sol.coupons.dto.PurchaseDetails;
import com.sol.coupons.entities.PurchaseEntity;
import com.sol.coupons.enums.ErrorTypes;
import com.sol.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchasesDetailsLogic {

    private IPurchasesDal purchasesDal;
    @Autowired
    public void PurchasesDetailsLogic(PurchasesLogic purchasesLogic){
        this.purchasesDal = purchasesDal;
    }

    public PurchaseDetails getPurchaseDetailsByPurchaseId(int id) throws ServerException {
        PurchaseEntity purchaseEntity = purchasesDal.findById(id).get();
        if(purchaseEntity == null){
            throw new ServerException(ErrorTypes.ID_DOESNT_EXIST,id+"");
        }
        float totalPurchaseCost = purchaseEntity.getAmount() * purchaseEntity.getCoupon().getPrice();
        PurchaseDetails purchaseDetails = new PurchaseDetails(purchaseEntity.getId(),purchaseEntity.getCoupon().getId(),purchaseEntity.getAmount(),purchaseEntity.getTimeStamp(),totalPurchaseCost,purchaseEntity.getCoupon().getTitle(),purchaseEntity.getCoupon().getDescription(),purchaseEntity.getCoupon().getImageUrl(),purchaseEntity.getCoupon().getEndDate());
        return purchaseDetails;
    }
//    public List<PurchaseDetails> getPurchaseDetailsByCustomer(int customerId) throws ServerException {
//        return iPurchasesDal.getPurchaseDetailsByCustomerId(customerId);
//    }
//
//    public PurchaseDetails getPurchaseDetailsById(int purchaseId) throws ServerException {
//        return IPurchasesDal.getPurchaseDetailsById(purchaseId);
//    }
}
