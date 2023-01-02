package com.example.reactivedemo.service;

//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.reactivedemo.Repository.CustomerRepository;
import com.example.reactivedemo.dao.CustomerDao;
import com.example.reactivedemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.LongSupplier;

@Service
public class CustomerService {
//    @Autowired
//    public CustomerDao customerDao;
//    // GET Methods for List:
//    public List<Customer> loadAllCustomers(){
//        return customerDao.getCustomersList();
//    }
//    public Customer loadCustomerByID(String id){
//        List<Customer> listOfCustomers = loadAllCustomers();
//        for(Customer customer:listOfCustomers){
//            if(customer.getId().equals(id)){
//                return customer;
//            }
//        }
//        return null;
//    }
    private final CustomerRepository customerRepository;
    private final LongSupplier getEpochSecond = () -> Instant.now()
            .getEpochSecond();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Mono<String> createNewCustomer(Customer customer) {
//        customer.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(customerRepository.save(customer))
                .thenReturn("SUCCESS")
                .onErrorReturn("FAIL");
    }

    public Mono<Customer> getCustomerByCustomerId(String id) {
        return Mono.fromFuture(customerRepository.getCustomerByID(id))
                .doOnSuccess(Objects::requireNonNull)
                .onErrorReturn(new Customer());
    }

    public Mono<String> updateExistingCustomer(Customer customer) {
//        customer.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(customerRepository.getCustomerByID(customer.getId()))
                .doOnSuccess(Objects::requireNonNull)
                .doOnNext(__ -> customerRepository.updateCustomer(customer))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn("SUCCESS")
                .onErrorReturn("FAIL");
    }

    public Mono<String> updateExistingOrCreateCustomer(Customer customer) {
//        customer.setCreatedTimeStamp(getEpochSecond.getAsLong());
        return Mono.fromFuture(customerRepository.updateCustomer(customer))
                .thenReturn("SUCCESS")
                .onErrorReturn("FAIL");
    }

    public Mono<String> deleteCustomerByCustomerId(String id) {
        return Mono.fromFuture(customerRepository.deleteCustomerById(id))
                .doOnSuccess(Objects::requireNonNull)
                .thenReturn("SUCCESS")
                .onErrorReturn("FAIL");
    }
    public Flux<Customer> getCustomerList() {
        return Flux.from(customerRepository.getAllCustomer()
                        .items())
                .onErrorReturn(new Customer());
    }
}
