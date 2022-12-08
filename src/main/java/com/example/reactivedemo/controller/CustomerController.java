package com.example.reactivedemo.controller;

import com.example.reactivedemo.dto.Customer;
import com.example.reactivedemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    public CustomerService customerService;
    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public List<Customer> getAllCustomersList(){
        return customerService.loadAllCustomers();
    }
}
