package com.example.reactivedemo.router;

import com.example.reactivedemo.handler.CustomerStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    @Autowired
    private CustomerStreamHandler handler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/customers", handler::getCustomers)
                .GET("/router/customers/{id}", handler::getCustomerByID)
                .POST("/router/customers/save",handler::saveCustomer)
                .GET("/router/customers/update/{id}", handler::updateCustomer)
                .build();
    }
}
