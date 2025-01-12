package com.sol.coupons.utils;

public class PermissionsUtils {

    public static boolean isAdmin(String userType) {
        if (userType.equals("ADMIN")) {
            return true;
        }
        return false;
    }

    public static boolean isCompany(String userType) {
        if (userType.equals("COMPANY")) {
            return true;
        }
        return false;
    }

    public static boolean isCustomer(String userType){
        if (userType.equals("CUSTOMER")) {
            return true;
        }
        return false;
    }
}
