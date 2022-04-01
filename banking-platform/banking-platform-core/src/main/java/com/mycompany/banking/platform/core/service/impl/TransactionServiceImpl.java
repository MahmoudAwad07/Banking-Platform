/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service.impl;

import com.mycompany.banking.platform.core.defines.ErrorCodes;
import com.mycompany.banking.platform.core.dto.TransactionDto;
import com.mycompany.banking.platform.core.dto.TransactionRequestDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.mapper.MapStructMapper;
import com.mycompany.banking.platform.core.model.Account;
import com.mycompany.banking.platform.core.model.Transaction;
import com.mycompany.banking.platform.core.repository.AccountRepository;
import com.mycompany.banking.platform.core.repository.TransactionRepository;
import com.mycompany.banking.platform.core.service.TransactionService;
import com.mycompany.banking.platform.core.service.ValidationService;
import java.util.Date;
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
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ApplicationContext applicationContext;
    private final MapStructMapper mapper;
    private final ValidationService validationService;

    
    @Autowired
    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, ApplicationContext applicationContext, MapStructMapper mapper, ValidationService validationService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.applicationContext = applicationContext;
        this.mapper = mapper;
        this.validationService = validationService;
    }
    

    @Override
    public TransactionDto createNewTransaction(TransactionRequestDto requestDto) throws BankingPlatformException {

        Long accountId = requestDto.getAccountId();
        Double transactionVlaue = requestDto.getTransactionValue();
        Integer transactionType = requestDto.getTransactionType();
        TransactionDto transactionDto = applicationContext.getBean(TransactionDto.class);

        Account foundAccount = validationService.isAccountFound(accountId);
        
        transactionDto.setTransactionValue(transactionVlaue);
        transactionDto.setTransactionDate(new Date());
        
        // 1 Deposit
        if(transactionType == 1){
            foundAccount.setBalance(foundAccount.getBalance() + transactionVlaue);  
        // 2 withdraw
        }else if(transactionType == 2){
            foundAccount.setBalance(foundAccount.getBalance() - transactionVlaue);
        }else {
            throw new BankingPlatformException(ErrorCodes.ERROR.INVALID_TRANSACTION_TYPE);
        }
        
        transactionDto.setType(transactionType);
        Transaction transactionEntity = mapper.toTransactionEntity(transactionDto);
        transactionEntity.setAccount(foundAccount);
//        Set<Transaction> transactions = foundAccount.getTransactions();
//        transactions.add(transactionEntity);

        return mapper.toTransactionDTO(transactionRepository.save(transactionEntity));
    }

}
