/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service;

import com.mycompany.banking.platform.core.dto.CustomerRequestDto;
import com.mycompany.banking.platform.core.dto.CustomerResponseDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import java.util.List;

/**
 *
 * @author mahmoud.awad
 */
public interface CustomerService {
    
    CustomerResponseDto addCustomer(CustomerRequestDto customer) throws BankingPlatformException;
    
    List<CustomerResponseDto> getAllCustomers();
    
    CustomerResponseDto getCustomerById(Long customerId) throws BankingPlatformException;
    
    void deleteCustomerById(Long customerId) throws BankingPlatformException;
    
    CustomerResponseDto updateCustomer(CustomerRequestDto customerDto) throws BankingPlatformException;
    
}
