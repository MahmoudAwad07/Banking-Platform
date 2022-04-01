/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service;

import com.mycompany.banking.platform.core.dto.AccountDto;
import com.mycompany.banking.platform.core.dto.AccountRequestDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;
import java.util.List;

/**
 *
 * @author mahmoud.awad
 */
public interface AccountService {
    
    AccountDto createNewAccount(AccountRequestDto accountRequestDto) throws BankingPlatformException;
    
    List<AccountDto> getAllAccounts();
    
    List<AccountDto> getAccountsByCustomerId(Long customerId) throws BankingPlatformException;
    
    AccountDto getAccountById(Long accountId) throws BankingPlatformException;
    
    void deleteByAccountId(Long accountId) throws BankingPlatformException;
}
