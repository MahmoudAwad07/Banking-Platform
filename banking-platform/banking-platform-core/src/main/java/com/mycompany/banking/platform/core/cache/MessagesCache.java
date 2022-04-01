/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.core.cache;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author mahmoud.awad
 */

@Data
@Component
@ConfigurationProperties
@PropertySource("classpath:messages.properties")
public class MessagesCache {
    
    private final Map<Integer, String > messages = new HashMap<>();
    
    
    public String getMessage(Integer errorCode){
        return messages.get(-1 * errorCode);
    }
    
    
    
    
}
