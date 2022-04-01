/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.webservice.controller;

import com.mycompany.banking.platform.core.cache.MessagesCache;
import com.mycompany.banking.platform.core.defines.ErrorCodes;
import com.mycompany.banking.platform.core.dto.BaseResponse;
import com.mycompany.banking.platform.core.dto.TransactionDto;
import com.mycompany.banking.platform.core.dto.TransactionRequestDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.logger.BankingPlatformLogger;
import com.mycompany.banking.platform.core.service.TransactionService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mahmoud.awad
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;
    private final MessagesCache messagesCache;
    
    
    @Autowired
    public TransactionController(TransactionService transactionService, MessagesCache messagesCache) {
        this.transactionService = transactionService;
        this.messagesCache = messagesCache;
    }
    
    @PostMapping("/new")
    public BaseResponse<TransactionDto> createNewTransaction(@Valid @RequestBody TransactionRequestDto newTransaction) throws BankingPlatformException {

        BankingPlatformLogger.info("Received Request to create new Transaction: " + newTransaction);
        TransactionDto createdTransaction = transactionService.createNewTransaction(newTransaction);
        BankingPlatformLogger.info("Transaction Created Successfully");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), createdTransaction);
    }
    
    
}
