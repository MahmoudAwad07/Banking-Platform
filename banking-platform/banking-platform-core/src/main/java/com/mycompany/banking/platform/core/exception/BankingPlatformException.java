/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.exception;

/**
 *
 * @author mahmoud.awad
 */
public class BankingPlatformException extends Exception {
    
    protected Integer errorCode;
    protected String errorMessage;

    public BankingPlatformException() {
        
    }

    public BankingPlatformException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public BankingPlatformException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public BankingPlatformException(Integer errorCode, Exception cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
