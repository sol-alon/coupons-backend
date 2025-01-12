package com.sol.coupons.exceptions;

import com.sol.coupons.dto.ErrorBean;
import com.sol.coupons.enums.ErrorTypes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;



// Exception handler class
@RestControllerAdvice
public class ServerExceptionHandler {

    //// Response - Object in Spring
    @ExceptionHandler
    public ErrorBean toResponse(Throwable throwable , HttpServletResponse response) {

        if (throwable instanceof ServerException) {
            ServerException appException = (ServerException) throwable;

            ErrorTypes errorType = appException.getErrorType();
            int errorNumber = errorType.getInternalError();
            int httpStatus = errorType.getHttpStatus();
            response.setStatus(httpStatus);
            String errorMessage = errorType.getClientErrorMessage();

            ErrorBean errorBean = new ErrorBean(httpStatus, errorMessage);
            if (appException.getErrorType().isShowStackTrace()) {
                appException.printStackTrace();
            }

            return errorBean;
        }
        ErrorBean errorBean = new ErrorBean(500, "Internal error");
        throwable.printStackTrace();
        response.setStatus(500);

        return errorBean;
    }

//    @ExceptionHandler(ServerException.class)
//    public ResponseEntity<Object> handleServerException(ServerException e) {
//        System.out.println("ServerException problem: " + e.getErrorType().toString());
//        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleGeneralException(Exception e) {
//        System.out.println("General problem: " + e.getMessage());
//        return new ResponseEntity<Object>("something wrong happen please try again latter", HttpStatus.BAD_REQUEST);
//    }
}