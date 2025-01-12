package com.sol.coupons.exceptions;
import com.sol.coupons.enums.ErrorTypes;

public class ServerException extends Exception{
    private ErrorTypes errorType;
    public ServerException(ErrorTypes errorType, String message){
        super(message);
        this.errorType = errorType;
    }
    public ServerException(ErrorTypes errorType, Exception e){
        super(e);
        this.errorType = errorType;
    }
    public ServerException(ErrorTypes errorType, Exception e, String message){
        super(message,e);
        this.errorType = errorType;
    }

    public ServerException(ErrorTypes errorType){
        this(errorType, "");
    }

    public ErrorTypes getErrorType() {
        return errorType;
    }
}