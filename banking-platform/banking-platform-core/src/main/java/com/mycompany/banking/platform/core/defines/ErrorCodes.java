/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.defines;

/**
 *
 * @author mahmoud.awad
 */
public class ErrorCodes {
    
    public static final class ERROR {

        public static final Integer UNKNOWN_ERROR = -100;
        public static final Integer INVALID_INITIAL_BALANCE = -101;
        public static final Integer CUSTOMER_NOT_FOUND = -102;
        public static final Integer INVALID_INPUT = -103;
        public static final Integer ACCOUNT_NOT_FOUND = -104;
        public static final Integer INVALID_TRANSACTION_TYPE = -105;
        public static final Integer CUSTOMER_ALREADY_EXISTS = -106;
        public static final Integer REQUIRED_ATTRIBUTE_MISSING = -107;
        public static final Integer INVALID_PARAMETER = -108;
        
    }

    public static final class SUCCESS {

        public static final Integer SUCCESS_CODE = 00;
    }
    
}
