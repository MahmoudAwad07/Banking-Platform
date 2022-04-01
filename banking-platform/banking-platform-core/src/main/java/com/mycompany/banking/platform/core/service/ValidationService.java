/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service;

import com.mycompany.banking.platform.core.dto.CustomerRequestDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.model.Account;
import com.mycompany.banking.platform.core.model.Customer;

/**
 *
 * @author mahmoud.awad
 */
public interface ValidationService {
    
    Boolean isCustomerNotFound(CustomerRequestDto customer) throws BankingPlatformException;
    
    Customer isCustomerFound(Long customerId) throws BankingPlatformException;
    
    Account isAccountFound(Long accountId) throws BankingPlatformException;
    
}
