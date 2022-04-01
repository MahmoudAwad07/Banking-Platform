/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.mapper;

import com.mycompany.banking.platform.core.dto.AccountDto;
import com.mycompany.banking.platform.core.dto.CustomerRequestDto;
import com.mycompany.banking.platform.core.dto.CustomerResponseDto;
import com.mycompany.banking.platform.core.dto.TransactionDto;
import com.mycompany.banking.platform.core.model.Account;
import com.mycompany.banking.platform.core.model.Customer;
import com.mycompany.banking.platform.core.model.Transaction;
import java.util.List;
import org.mapstruct.Mapper;



/**
 *
 * @author mahmoud.awad
 */
@Mapper(componentModel = "spring")
public interface MapStructMapper {
    
        CustomerResponseDto toCustomerDTO(Customer customer);

	List<CustomerResponseDto> toCustomersDTOs(List<Customer> customers);

	Customer toCustomerEntity(CustomerResponseDto customerDto);
        
        AccountDto toAccountDTO(Account account);

	List<AccountDto> toAccountsDTOs(List<Account> accounts);

	Account toAccountEntity(AccountDto accountDto);
        
        TransactionDto toTransactionDTO(Transaction transaction);

	List<TransactionDto> toTransactionsDTOs(List<Transaction> transactions);

	Transaction toTransactionEntity(TransactionDto transactionDto);
        
        Customer toCustomerEntity(CustomerRequestDto customerRequestDto);
    
}
