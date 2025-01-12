package com.sol.coupons.utils;

public class StatisticsUtils {

    public static void sendStatistics(String username, String action) {
        new Thread(()-> {System.out.println("Sending statistics: " + action + " " + username);}).start();
    }
}
