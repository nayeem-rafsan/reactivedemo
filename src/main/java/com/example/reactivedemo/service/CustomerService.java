package com.example.reactivedemo.service;

import com.example.reactivedemo.dao.CustomerDao;
import com.example.reactivedemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    public CustomerDao customerDao;

    public Flux<Customer> loadAllCustomers(){
        return customerDao.getCustomers();
    }
}
