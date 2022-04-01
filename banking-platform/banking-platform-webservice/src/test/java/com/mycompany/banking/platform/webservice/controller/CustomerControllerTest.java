/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.banking.platform.webservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.banking.platform.core.cache.MessagesCache;
import com.mycompany.banking.platform.core.dto.AccountDto;
import com.mycompany.banking.platform.core.dto.BaseResponse;
import com.mycompany.banking.platform.core.dto.CustomerRequestDto;
import com.mycompany.banking.platform.core.dto.CustomerResponseDto;
import com.mycompany.banking.platform.core.dto.TransactionDto;
import com.mycompany.banking.platform.core.service.CustomerService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author mahmoud.awad
 */
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CustomerService customerService;
    
    @MockBean
    private MessagesCache messagesCache;
    
    private List<CustomerResponseDto> returnedCustomers = new ArrayList<>();
    private List<AccountDto> accounts = new ArrayList<>();
    private CustomerResponseDto customerResponseDto_1 = new CustomerResponseDto(new Long(1) , "Mahmoud", "Awad", "Mahmoud.Awad@asset.com.eg", new Date() , new Date() , new ArrayList<>());
    private CustomerResponseDto customerResponseDto_2 =  new CustomerResponseDto(new Long(2) , "Mahmoud_2", "Awad_2", "Mahmoud.Awad_2@asset.com.eg", new Date(), new Date(), new ArrayList<>());
    private CustomerRequestDto customerRequestDto_1 = new CustomerRequestDto( new Long(0) , "Mahmoud", "Awad", "Mahmoud.Awad@asset.com.eg");
    private CustomerRequestDto customerRequestDto_2 = new CustomerRequestDto( null , "Mahmoud", "Awad", "Mahmoud.Awad@asset.com.eg");
    private CustomerRequestDto customerRequestDto_3 = new CustomerRequestDto( null , "", "Awad", "Mahmoud.Awad@asset.com.eg");
    private AccountDto accountDto_1 = new AccountDto( new Long(1) , 100.00 , new Date() , new Date() , new ArrayList<>());
    private List<TransactionDto> transactions = new ArrayList<>();
    private TransactionDto transationDto_1 = new TransactionDto(new Long(1) , 100.00 , 1 , new Date());
    private Long customerId_1 = new Long(1) ;
    private Long customerId_2 = new Long(0) ;
    private Long customerId_3 = null;
    
    
    @Test
    public void addNewCustomer_basic() throws Exception{
        
        Mockito.when(customerService.addCustomer(customerRequestDto_1)).thenReturn(customerResponseDto_1);
        Mockito.when(messagesCache.getMessage(ArgumentMatchers.anyInt())).thenReturn("Success.");
        
        BaseResponse<CustomerResponseDto> mockedResponse = new BaseResponse<>("0", "Success." , customerResponseDto_1);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        String mockedRequestAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerRequestDto_1);
        String mockedResponseAsString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockedResponse);
        
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.post("/customers/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockedRequestAsString)
                .contentType(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(mockedResponseAsString))
                .andReturn();
        
    }
    
    @Test
    public void addNewCustomer_withNullId() throws Exception{
        
        Mockito.when(customerService.addCustomer(customerRequestDto_2)).thenReturn(customerResponseDto_1);
        Mockito.when(messagesCache.getMessage(ArgumentMatchers.anyInt())).thenReturn("Success.");
        
        BaseResponse<CustomerResponseDto> mockedResponse = new BaseResponse<>("0", "Success." , customerResponseDto_1);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        String mockedRequestAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(customerRequestDto_2);
        String mockedResponseAsString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockedResponse);
        
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.post("/customers/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockedRequestAsString)
                .contentType(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(mockedResponseAsString))
                .andReturn();
        
    }
    
    
    @Test
    public void getCustomerById_basic() throws Exception{
        
        Mockito.when(customerService.getCustomerById(ArgumentMatchers.anyLong())).thenReturn(customerResponseDto_2);
        Mockito.when(messagesCache.getMessage(ArgumentMatchers.anyInt())).thenReturn("Success.");
        
        BaseResponse<CustomerResponseDto> mockedResponse = new BaseResponse<>("0", "Success." , customerResponseDto_2);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String mockedResponseAsString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockedResponse);
        
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.get("/customers/get/1")
                .accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(mockedResponseAsString))
                .andReturn();
        
    }
    
    
    @Test
    public void getAllCustomers_basic() throws Exception{
        
        
        returnedCustomers.add(customerResponseDto_1);
        returnedCustomers.add(customerResponseDto_2);
        
        BaseResponse<List<CustomerResponseDto>> mockedResponse = new BaseResponse<>("0", "Success." , returnedCustomers);
        
        
        Mockito.when(customerService.getAllCustomers()).thenReturn(returnedCustomers);
        Mockito.when(messagesCache.getMessage(ArgumentMatchers.anyInt())).thenReturn("Success.");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String mockedResponseAsString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockedResponse);
        
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.get("/customers/list").accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(mockedResponseAsString))
                .andReturn();
        
    }
    
    @Test
    public void getAllCustomers_empty() throws Exception{
        
        
        Mockito.when(customerService.getAllCustomers()).thenReturn(returnedCustomers);
        Mockito.when(messagesCache.getMessage(ArgumentMatchers.anyInt())).thenReturn("Success.");
        
        BaseResponse<List<CustomerResponseDto>> mockedResponse = new BaseResponse<>("0", "Success." , returnedCustomers);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String mockedResponseAsString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockedResponse);
        
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.get("/customers/list").accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(mockedResponseAsString))
                .andReturn();
        
    }
    
    @Test
    public void getAllCustomers_withAccounts() throws Exception {
        
        accounts.add(accountDto_1);
        customerResponseDto_1.setAccounts(accounts);
        returnedCustomers.add(customerResponseDto_1);
        returnedCustomers.add(customerResponseDto_2);
        
        BaseResponse<List<CustomerResponseDto>> mockedResponse = new BaseResponse<>("0", "Success." , returnedCustomers);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String mockedResponseAsString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockedResponse);
        
        
        Mockito.when(customerService.getAllCustomers()).thenReturn(returnedCustomers);
        Mockito.when(messagesCache.getMessage(ArgumentMatchers.anyInt())).thenReturn("Success.");
        
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.get("/customers/list").accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(mockedResponseAsString))
                .andReturn();
        
    }
    
    
    @Test
    public void getAllCustomers_withTransactions() throws Exception{
        
        transactions.add(transationDto_1);
        accountDto_1.setTransactions(transactions);
        accounts.add(accountDto_1);
        customerResponseDto_1.setAccounts(accounts);
        returnedCustomers.add(customerResponseDto_1);
        returnedCustomers.add(customerResponseDto_2);
        
        BaseResponse<List<CustomerResponseDto>> mockedResponse = new BaseResponse<>("0", "Success." , returnedCustomers);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String mockedResponseAsString =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockedResponse);
        
        
        Mockito.when(customerService.getAllCustomers()).thenReturn(returnedCustomers);
        Mockito.when(messagesCache.getMessage(ArgumentMatchers.anyInt())).thenReturn("Success.");
        
        RequestBuilder requestBuilder =  MockMvcRequestBuilders.get("/customers/list").accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(mockedResponseAsString))
                .andReturn();
        
    }
    
}
