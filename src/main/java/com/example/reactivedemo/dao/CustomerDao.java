package com.example.reactivedemo.dao;

import com.example.reactivedemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Component
public class CustomerDao {
    // Reactive approach
    public Flux<Customer> getCustomers(){
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i-> System.out.println("customer: "+i))
                .map(i-> new Customer(i+"", "customer: "+i));
//        return IntStream.rangeClosed(1,50)
//                .mapToObj(i-> new Customer(i, "customer:"+i)).collect(Collectors.toList());
    }
    // Functional approach
    public List<Customer> getCustomersList(){
        return IntStream.rangeClosed(1,10)
                .mapToObj(i-> new Customer(i+"", "customer: "+i)).collect(Collectors.toList());
    }
}
