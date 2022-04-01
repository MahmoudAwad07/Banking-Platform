/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.webservice.controller;

import com.mycompany.banking.platform.core.cache.MessagesCache;
import com.mycompany.banking.platform.core.defines.ErrorCodes;
import com.mycompany.banking.platform.core.dto.BaseResponse;
import com.mycompany.banking.platform.core.dto.CustomerRequestDto;
import com.mycompany.banking.platform.core.dto.CustomerResponseDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.logger.BankingPlatformLogger;
import com.mycompany.banking.platform.core.service.CustomerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author mahmoud.awad
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final MessagesCache messagesCache;

    @Autowired
    public CustomerController(CustomerService customerService, MessagesCache messagesCache) {
        this.customerService = customerService;
        this.messagesCache = messagesCache;
    }

    @PostMapping("/add")
    public BaseResponse<CustomerResponseDto> addNewCustomer(@Valid @RequestBody CustomerRequestDto customer) throws BankingPlatformException {

        BankingPlatformLogger.info("Received Request to add new Customer: " + customer);
        CustomerResponseDto savedCustomer = customerService.addCustomer(customer);
        BankingPlatformLogger.info(" Customer Added Successfully");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), savedCustomer);
    }

    @GetMapping("/list")
    public BaseResponse<List<CustomerResponseDto>> getAllCustomers() {

        BankingPlatformLogger.info("Received Request to get All Customers ");
        List<CustomerResponseDto> allCustomers = customerService.getAllCustomers();
        BankingPlatformLogger.info(" All Customers retured Successfully ");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), allCustomers);
    }

    @GetMapping("/get/{customerId}")
    public BaseResponse<CustomerResponseDto> getCustomerById(@PathVariable(value = "customerId") Long customerId) throws BankingPlatformException {

        BankingPlatformLogger.info("Received Request get Customer By Id: " + customerId);
        CustomerResponseDto customer = customerService.getCustomerById(customerId);
        BankingPlatformLogger.info(" Customer returned Successfully " + customer);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), customer);
    }

    @PutMapping("/update")
    public BaseResponse<CustomerResponseDto> updateCustomer(@Valid @RequestBody CustomerRequestDto customerDto) throws BankingPlatformException {
        
        BankingPlatformLogger.info("Received Request update Customer for customer : " + customerDto);
        CustomerResponseDto updatedCustomer = customerService.updateCustomer(customerDto);
        BankingPlatformLogger.info(" Customer updated Successfully " + updatedCustomer);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS_CODE.toString() , messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE) , updatedCustomer);
    }

    @DeleteMapping("/delete/{customerId}")
    public BaseResponse deleteCustomer(@PathVariable(value = "customerId") Long customerId) throws BankingPlatformException {
        
        BankingPlatformLogger.info("Received Request delete Customer By Id: " + customerId);
        customerService.deleteCustomerById(customerId);
        BankingPlatformLogger.info("Customer Deleted Successfully ");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), null);
    }

}
