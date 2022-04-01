/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service.impl;

import com.mycompany.banking.platform.core.defines.ErrorCodes;
import com.mycompany.banking.platform.core.dto.CustomerRequestDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.model.Account;
import com.mycompany.banking.platform.core.model.Customer;
import com.mycompany.banking.platform.core.repository.AccountRepository;
import com.mycompany.banking.platform.core.repository.CustomerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.mycompany.banking.platform.core.service.ValidationService;
import org.springframework.stereotype.Service;

/**
 *
 * @author mahmoud.awad
 */
@Service
public class ValidationServiceImpl implements ValidationService{
    
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    
    @Autowired
    public ValidationServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Boolean isCustomerNotFound(CustomerRequestDto customer) throws BankingPlatformException {
        
        Optional<Customer> foundCustomer = customerRepository.findByNameAndSurNameOrEmail(customer.getName(), customer.getSurName(), customer.getEmail());

        if (foundCustomer.isPresent()) {
            throw new BankingPlatformException(ErrorCodes.ERROR.CUSTOMER_ALREADY_EXISTS);
        }
        return true;
         
    }

    @Override
    public Customer isCustomerFound(Long customerId) throws BankingPlatformException {
        
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new BankingPlatformException(ErrorCodes.ERROR.CUSTOMER_NOT_FOUND);
        }

        return customerOptional.get();
        
    }

    @Override
    public Account isAccountFound(Long accountId) throws BankingPlatformException {
        
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        
        if(!accountOptional.isPresent()){
            throw new BankingPlatformException(ErrorCodes.ERROR.ACCOUNT_NOT_FOUND);
        }
        
        return accountOptional.get();
    }
    
}
