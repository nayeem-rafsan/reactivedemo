package com.example.reactivedemo.handler;

import com.example.reactivedemo.dao.CustomerDao;
import com.example.reactivedemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CustomerStreamHandler {
    @Autowired
    private CustomerDao customerDao;

    public Mono<ServerResponse> getCustomers(ServerRequest request){
        Flux<Customer> customers = customerDao.getCustomers();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customers, Customer.class);
    }
    public Mono<ServerResponse> getCustomerByID(ServerRequest request){
        int customerID = Integer.parseInt(request.pathVariable("id"));
        Mono<Customer> customer = customerDao.getCustomers().filter(e-> e.getId()==customerID).next();
        return ServerResponse.ok().body(customer, Customer.class);
    }
    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(saveResponse,String.class);
    }
    public Mono<ServerResponse> updateCustomer(ServerRequest request){
        int customerID = Integer.parseInt(request.pathVariable("id"));
        Mono<Customer> customerMono = customerDao.getCustomers().filter(e-> e.getId()==customerID).next();
        Mono<Customer> updatedMono = request.bodyToMono(Customer.class);
        customerMono = updatedMono.map(e -> {
            return e;
        });
//
//        Mono<String> name = updatedMono.map(Customer::getName);
//        System.out.println(name.toString());
//        System.out.println(name.toString());

//        customerMono = customerMono.name(name.toString());
//        System.out.println(customerMono.toString());
        return ServerResponse.ok().body(customerMono, Customer.class);
    }
}
