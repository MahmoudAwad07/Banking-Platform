/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service.impl;

import com.mycompany.banking.platform.core.dto.CustomerRequestDto;
import com.mycompany.banking.platform.core.dto.CustomerResponseDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.mapper.MapStructMapper;
import com.mycompany.banking.platform.core.model.Customer;
import com.mycompany.banking.platform.core.repository.CustomerRepository;
import com.mycompany.banking.platform.core.service.CustomerService;
import com.mycompany.banking.platform.core.service.ValidationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author mahmoud.awad
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationContext applicationContext;
    private final MapStructMapper mapper;
    private final ValidationService validationService;

    
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ApplicationContext applicationContext, MapStructMapper mapper, ValidationService validationService) {
        this.customerRepository = customerRepository;
        this.applicationContext = applicationContext;
        this.mapper = mapper;
        this.validationService = validationService;
    }

    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customer) throws BankingPlatformException {

        validationService.isCustomerNotFound(customer);
        Customer customerEntity = mapper.toCustomerEntity(customer);
        return mapper.toCustomerDTO(customerRepository.save(customerEntity));
    }
    
    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return mapper.toCustomersDTOs(customerRepository.findAll());
    }

    @Override
    public CustomerResponseDto getCustomerById(Long customerId) throws BankingPlatformException {

        Customer foundCustomer = validationService.isCustomerFound(customerId);
        return mapper.toCustomerDTO(foundCustomer);
    }

    @Override
    public void deleteCustomerById(Long customerId) throws BankingPlatformException {

        validationService.isCustomerFound(customerId);
        customerRepository.deleteById(customerId);

    }

    @Override
    public CustomerResponseDto updateCustomer(CustomerRequestDto customerDto) throws BankingPlatformException {


        Customer foundCustomer = validationService.isCustomerFound(customerDto.getId());
        updateCustomerEntity(foundCustomer, customerDto);
        
        return mapper.toCustomerDTO(customerRepository.save(foundCustomer));
    }

    private void updateCustomerEntity(Customer customerEntity, CustomerRequestDto customerDto) {

        customerEntity.setName(customerDto.getName());
        customerEntity.setSurName(customerDto.getSurName());
        customerEntity.setEmail(customerDto.getEmail());
    }

}
