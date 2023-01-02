package com.example.reactivedemo.controller;

import com.example.reactivedemo.Repository.CustomerRepository;
import com.example.reactivedemo.dto.Customer;
import com.example.reactivedemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class CustomerController {
//    @Autowired
//    public CustomerService customerService;
//    @GetMapping(value = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public List<Customer> getAllCustomersList(){
//        return customerService.loadAllCustomers();
//    }



    // DynamoDb controller
//    @Autowired
//    public CustomerRepository customerRepository;
//    @PostMapping("/customer")
//    public Customer saveCustomer(@RequestBody Customer customer) {
//        return customerRepository.save(customer);
//    }
//    @GetMapping("/customer")
//    public List<Customer> getAllCustomer(){
//        return customerRepository.getAllCustomers();
//    }
//    @GetMapping("/customer/{id}")
//    public Customer getEmployee(@PathVariable("id") String customerId) {
//        return customerRepository.getCustomerById(customerId);
//    }
//
//    @DeleteMapping("/customer/{id}")
//    public String deleteCustomer(@PathVariable("id") String customerId) {
//        return  customerRepository.delete(customerId);
//    }
//
//    @PutMapping("/customer/{id}")
//    public String updateEmployee(@PathVariable("id") String customerId, @RequestBody Customer customer) {
//        return customerRepository.updateCustomer(customerId,customer);
//    }

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @PostMapping("/customer")
    public Mono<String> saveCustomer(@RequestBody Customer customer) {
        return customerService.createNewCustomer(customer);
    }
    @GetMapping("/customer")
    public Flux<Customer> getAllCustomer(){
        return customerService.getCustomerList();
    }
    @GetMapping("/customer/{id}")
    public Mono<Customer> getCustomerById(@PathVariable("id") String id) {
        return customerService.getCustomerByCustomerId(id);
    }
//
    @DeleteMapping("/customer/{id}")
    public Mono<String> deleteCustomer(@PathVariable("id") String customerId) {
        return  customerService.deleteCustomerByCustomerId(customerId);
    }
//
    @PutMapping("/customer/{id}")
    public Mono<String> updateEmployee(@PathVariable("id") String id, @RequestBody Customer customer) {
        return customerService.updateExistingCustomer(customer);
    }
}
