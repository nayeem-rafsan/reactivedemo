package com.example.reactivedemo.controller;

import com.example.reactivedemo.dto.Customer;
import com.example.reactivedemo.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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
//    @GetMapping("/test")
//    public Flux<Object> testMethod(){
//        return customerService.getCustomerList().map(
//                c -> {
//                    if(c.getId().equals("3")){
//                        return new Exception("");
//                    }
//                    else {
//                        return c;
//                    }
//                }
//        ).onErrorResume(e->System.out::println);
//    }
    @GetMapping("/customer/{id}")
    public Mono<Customer> getCustomerById(@PathVariable("id") String id) {
        return customerService.getCustomerByCustomerId(id);
    }
//
    @GetMapping(value = "/t",produces="application/json")
    public Flux<String> test() {
        Flux<String> nameFlux1 = Flux.just("dummy\n");
        Flux<String> nameFlux2 = Flux.just("dummy1\n").delayElements(Duration.ofSeconds(10));
        Flux<String> nameFlux3 = Flux.just("dummy2\n").delayElements(Duration.ofSeconds(2));
        Flux<String> nameFlux4 = Flux.just("dummy3").delayElements(Duration.ofSeconds(5));
//        return Flux.just(Flux.just("1"), Flux.just("2")).delayElements(Duration.ofSeconds(10)).toString();
        return Flux.merge(nameFlux1,nameFlux2,nameFlux3,nameFlux4);
    }
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
