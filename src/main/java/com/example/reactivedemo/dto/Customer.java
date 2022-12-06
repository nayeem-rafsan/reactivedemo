package com.example.reactivedemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private int id;
    private String name;
//    public Customer(int id, String name){
//        this.id = id;
//        this.name = name;
//    }
}
