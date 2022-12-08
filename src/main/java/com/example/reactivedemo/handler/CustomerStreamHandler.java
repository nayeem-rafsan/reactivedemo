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
        int customerID = Integer.parseInt(request.pathVariable("input"));
        Mono<Customer> customer = customerDao.getCustomers().filter(e-> e.getId()==customerID).next();
        return ServerResponse.ok().body(customer, Customer.class);
    }
    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(saveResponse,String.class);
    }

}
