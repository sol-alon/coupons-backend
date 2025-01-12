package com.sol.coupons.tasks;

import com.sol.coupons.exceptions.ServerException;
import com.sol.coupons.logic.CouponsLogic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredCouponsTask {

    private final CouponsLogic couponsLogic;

    public DeleteExpiredCouponsTask(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    @Scheduled(cron = "0 2 * * *")
    public void deleteExpiredCoupons() {
        try {
            couponsLogic.deleteExpiredCoupons();
        } catch (ServerException e) {
            System.out.println("deleteExpiredCouponsTask failed: "+ e);
        }
    }
}
