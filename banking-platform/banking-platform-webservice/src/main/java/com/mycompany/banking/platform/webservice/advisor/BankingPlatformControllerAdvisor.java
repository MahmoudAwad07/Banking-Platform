/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.webservice.advisor;

import com.mycompany.banking.platform.core.cache.MessagesCache;
import com.mycompany.banking.platform.core.defines.ErrorCodes;
import com.mycompany.banking.platform.core.dto.BaseResponse;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.logger.BankingPlatformLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author mahmoud.awad
 */
@ControllerAdvice
public class BankingPlatformControllerAdvisor {

    private final MessagesCache messagesCache;

    @Autowired
    public BankingPlatformControllerAdvisor(MessagesCache messagesCache) {
        this.messagesCache = messagesCache;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse> handleAllExceptions(Exception ex, WebRequest req) {
        BankingPlatformLogger.debugError(" An error has occured with message" + ex.getMessage());
        BaseResponse<String> response = new BaseResponse(ErrorCodes.ERROR.UNKNOWN_ERROR.toString(), messagesCache.getMessage(ErrorCodes.ERROR.UNKNOWN_ERROR), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BankingPlatformException.class)
    public final ResponseEntity<BaseResponse> handleBankingPlatformExceptions(BankingPlatformException ex, WebRequest req) {
        BankingPlatformLogger.debugError("An error has occured with Error Code : " + ex.getErrorCode());
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ex.getErrorCode().toString());
        response.setStatusDescription(messagesCache.getMessage(ex.getErrorCode()));
        BankingPlatformLogger.debugError("An error has occured with error code: " + ex.getErrorCode() + " and error message: " + response.getStatusDescription());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<BaseResponse> handleMethodArgumentTypeMismatchExceptions(MethodArgumentTypeMismatchException ex, WebRequest req) {
        BankingPlatformLogger.debugError("An error has occured with message : " + ex.getMessage());
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ErrorCodes.ERROR.INVALID_INPUT.toString());
        response.setStatusDescription(messagesCache.getMessage(ErrorCodes.ERROR.INVALID_INPUT));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<BaseResponse> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex, WebRequest req) {
        BankingPlatformLogger.debugError("An error has occured with message : " + ex.getMessage());
        BaseResponse<String> response = new BaseResponse();

        if (ex.getFieldError() == null) {
            response.setStatusCode(ErrorCodes.ERROR.REQUIRED_ATTRIBUTE_MISSING.toString());
            response.setStatusDescription(messagesCache.getMessage(ErrorCodes.ERROR.REQUIRED_ATTRIBUTE_MISSING));
        } else {
            response.setStatusCode(ErrorCodes.ERROR.INVALID_PARAMETER.toString());
            response.setStatusDescription(ex.getFieldError().getDefaultMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
