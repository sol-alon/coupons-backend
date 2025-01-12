package com.sol.coupons.enums;
public enum ErrorTypes {
    GENERAL_ERROR(1000, "A general error has occurred. Please try again later",true,500),
    UNAUTHORIZED(1001, "Unauthorized. Invalid user name or password",true,401),
    INVALID_COUPON_DESCRIPTION(1002, "Invalid coupon description",true,400),
    INVALID_COUPON_AMOUNT(1003,"Invalid coupon amount",true,400),
    INVALID_COUPON_PRICE(1004,"Invalid coupon price",true,400),
    INVALID_COMPANY_ID(1005,"Invalid company id",true,400),
    INVALID_COUPON_TITLE(1005 , "Invalid coupon title",true,400) ,
    INVALID_COUPON_START_END(1007,"Start date must be earlier than the End date ",true,400),
    INVALID_USER_NAME(1009,"Username is too long or too short",true,400),
    INVALID_USER_PASSWORD(1010,"Password length is Invalid",true,400),
    INVALID_CATEGORY_NAME(1012,"Invalid category",true,400),
    INVALID_NAME(1013,"Name is invalid",true,400),
    INVALID_PHONE(1014,"Invalid phone number",true,400),
    INVALID_ADDRESS(1015,"Address is not correct",true,400),
    INVALID_PURCHASES_AMOUNT(1016,"No coupons were chosen",true,400),
    INVALID_DATE(1017, "Date is invalid",true,400),
    ID_DOESNT_EXIST(1018, "The provided ID is null",true,400);

    private int internalError;
    private String clientErrorMessage;
    private boolean isShowStackTrace;
    private int httpStatus;

    ErrorTypes(int internalError, String clientErrorMessage, boolean isShowStackTrace, int httpStatus){
        this.internalError = internalError;
        this.clientErrorMessage = clientErrorMessage;
        this.isShowStackTrace = isShowStackTrace;
        this.httpStatus = httpStatus;
    }

    public int getInternalError() {
        return internalError;
    }

    public String getClientErrorMessage() {
        return clientErrorMessage;
    }

    public int getHttpStatus(){ return  httpStatus;}

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }
}
