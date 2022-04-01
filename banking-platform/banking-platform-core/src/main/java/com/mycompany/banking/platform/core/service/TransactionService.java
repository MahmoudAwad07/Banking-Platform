/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.service;

import com.mycompany.banking.platform.core.dto.TransactionDto;
import com.mycompany.banking.platform.core.dto.TransactionRequestDto;
import com.mycompany.banking.platform.core.exception.BankingPlatformException;

/**
 *
 * @author mahmoud.awad
 */
public interface TransactionService {
    
    TransactionDto createNewTransaction(TransactionRequestDto requestDto) throws BankingPlatformException;
    
}
