/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service.impl;

import com.mycompany.banking.platform.core.defines.ErrorCodes;
import com.mycompany.banking.platform.core.dto.AccountDto;
import com.mycompany.banking.platform.core.dto.AccountRequestDto;
import com.mycompany.banking.platform.core.dto.TransactionDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.mapper.MapStructMapper;
import com.mycompany.banking.platform.core.model.Account;
import com.mycompany.banking.platform.core.model.Customer;
import com.mycompany.banking.platform.core.model.Transaction;
import com.mycompany.banking.platform.core.repository.AccountRepository;
import com.mycompany.banking.platform.core.repository.CustomerRepository;
import com.mycompany.banking.platform.core.service.AccountService;
import com.mycompany.banking.platform.core.service.ValidationService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author mahmoud.awad
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final ApplicationContext applicationContext;
    private final MapStructMapper mapper;
    private final ValidationService validationService;

    @Autowired
    public AccountServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, ApplicationContext applicationContext, MapStructMapper mapper, ValidationService validationService) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.applicationContext = applicationContext;
        this.mapper = mapper;
        this.validationService = validationService;
    }

    @Override
    public AccountDto createNewAccount(AccountRequestDto accountRequestDto) throws BankingPlatformException {

        Long customerId = accountRequestDto.getCustomerId();
        Double initialCredit = accountRequestDto.getInitialCredit();
        AccountDto accountDto = applicationContext.getBean(AccountDto.class);;
        Account accountEntity = null;
        TransactionDto transactionDto = null;
        List<Transaction> transactions =  new ArrayList<>();

        Customer foundCustomer = validationService.isCustomerFound(customerId);

        if (initialCredit == 0) {

            accountDto.setBalance(Double.valueOf(0));
            accountEntity = mapper.toAccountEntity(accountDto);
            accountEntity.setCustomer(foundCustomer);

        } else if (initialCredit > 0) {

            accountDto.setBalance(initialCredit);
            transactionDto = buildTransactionDto(initialCredit);
            Transaction transactionEntity = mapper.toTransactionEntity(transactionDto);
            accountEntity = mapper.toAccountEntity(accountDto);
            accountEntity.setCustomer(foundCustomer);
            transactionEntity.setAccount(accountEntity);
            transactions.add(transactionEntity);
            accountEntity.setTransactions(transactions);

        } else {
            throw new BankingPlatformException(ErrorCodes.ERROR.INVALID_INITIAL_BALANCE);
        }

        return mapper.toAccountDTO(accountRepository.save(accountEntity));
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return mapper.toAccountsDTOs(accountRepository.findAll());
    }

    @Override
    public List<AccountDto> getAccountsByCustomerId(Long customerId) throws BankingPlatformException {

        Customer foundCustomer = validationService.isCustomerFound(customerId);
        return mapper.toAccountsDTOs(new ArrayList<>(foundCustomer.getAccounts()));
    }

    @Override
    public AccountDto getAccountById(Long accountId) throws BankingPlatformException {

        Account foundAccount = validationService.isAccountFound(accountId);
        return mapper.toAccountDTO(foundAccount);
    }

    @Override
    public void deleteByAccountId(Long accountId) throws BankingPlatformException {

        validationService.isAccountFound(accountId);
        accountRepository.deleteById(accountId);
    }

    private TransactionDto buildTransactionDto(Double initialCredit) {

        TransactionDto transactionDto = applicationContext.getBean(TransactionDto.class);

        transactionDto.setTransactionValue(initialCredit);
        transactionDto.setTransactionDate(new Date());
        transactionDto.setType(1); // 1 Deposit

        return transactionDto;
    }

}
