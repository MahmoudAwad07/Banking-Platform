/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.webservice.controller;

import com.mycompany.banking.platform.core.cache.MessagesCache;
import com.mycompany.banking.platform.core.defines.ErrorCodes;
import com.mycompany.banking.platform.core.dto.AccountDto;
import com.mycompany.banking.platform.core.dto.AccountRequestDto;
import com.mycompany.banking.platform.core.dto.BaseResponse;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import com.mycompany.banking.platform.core.logger.BankingPlatformLogger;
import com.mycompany.banking.platform.core.service.AccountService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mahmoud.awad
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    
    private final AccountService accountService; 
    private final MessagesCache messagesCache;

    @Autowired
    public AccountController(AccountService accountService, MessagesCache messagesCache) {
        this.accountService = accountService;
        this.messagesCache = messagesCache;
    }
    
    @PostMapping("/create")
    public BaseResponse<AccountDto> createNewAccount(@Valid @RequestBody AccountRequestDto newAccount) throws BankingPlatformException {

        BankingPlatformLogger.info("Received Request to create new Customer Account: " + newAccount);
        AccountDto newAccountDto = accountService.createNewAccount(newAccount);
        BankingPlatformLogger.info("Account Created Successfully");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), newAccountDto);
    }
    
    @GetMapping("/list")
    public BaseResponse<List<AccountDto>> getAllAccounts() {

        BankingPlatformLogger.info("Received Request to get All Accounts ");
        List<AccountDto> allAccounts = accountService.getAllAccounts();
        BankingPlatformLogger.info("All Accounts retured Successfully ");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), allAccounts);
    }

    @GetMapping("/list/{customerId}")
    public BaseResponse<List<AccountDto>> getAccountsByCustomerId(@PathVariable(value = "customerId") Long customerId) throws BankingPlatformException {

        BankingPlatformLogger.info("Received Request get Accounts By Customer Id: " + customerId);
        List<AccountDto> accounts = accountService.getAccountsByCustomerId(customerId);
        BankingPlatformLogger.info("Customer Accounts returned Successfully ");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), accounts);
    }
    
    @GetMapping("/get/{accountId}")
    public BaseResponse<AccountDto> getAccountById(@PathVariable(value = "accountId") Long accountId) throws BankingPlatformException {

        BankingPlatformLogger.info("Received Request get Account By Id: " + accountId);
        AccountDto account = accountService.getAccountById(accountId);
        BankingPlatformLogger.info(" Account returned Successfully " + account);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), account);
    }
    
    @DeleteMapping("/delete/{accountId}")
    public BaseResponse deleteAccountById(@PathVariable(value = "accountId") Long accountId) throws BankingPlatformException {
        
        BankingPlatformLogger.info("Received Request delete Account By Id: " + accountId);
        accountService.deleteByAccountId(accountId);
        BankingPlatformLogger.info("Account Deleted Successfully ");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS_CODE.toString(), messagesCache.getMessage(ErrorCodes.SUCCESS.SUCCESS_CODE), null);
    }
    
    
}
