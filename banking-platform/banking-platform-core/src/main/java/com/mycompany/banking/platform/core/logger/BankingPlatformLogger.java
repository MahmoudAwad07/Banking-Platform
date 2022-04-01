/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mahmoud.awad
 */
public class BankingPlatformLogger {
    
    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("debugLogger");
    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("errorLogger");

    public static void error(String msg, Throwable e) {
        DEBUG_LOGGER.error(msg);
        ERROR_LOGGER.error(msg, e);
    }

    public static void debugError(String msg) {
        DEBUG_LOGGER.error(msg);
    }

    public static void info(String msg) {
        DEBUG_LOGGER.info(msg);
    }

    public static void debug(String msg) {
        DEBUG_LOGGER.debug(msg);
    }
    
}
