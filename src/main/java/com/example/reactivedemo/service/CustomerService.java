package com.example.reactivedemo.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.reactivedemo.dao.CustomerDao;
import com.example.reactivedemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    public CustomerDao customerDao;
    // GET Methods for List:
    public List<Customer> loadAllCustomers(){
        return customerDao.getCustomersList();
    }
    public Customer loadCustomerByID(String id){
        List<Customer> listOfCustomers = loadAllCustomers();
        for(Customer customer:listOfCustomers){
            if(customer.getId().equals(id)){
                return customer;
            }
        }
        return null;
    }
}
