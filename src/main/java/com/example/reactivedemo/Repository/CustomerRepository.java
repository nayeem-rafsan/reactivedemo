package com.example.reactivedemo.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.reactivedemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;

@Repository
public class CustomerRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Customer save(Customer customer){
        dynamoDBMapper.save(customer);
        return customer;
    }
    public Customer getCustomerById(String id){
        return dynamoDBMapper.load(Customer.class, id);
    }
    public String delete(String id){
        Customer customer = dynamoDBMapper.load(Customer.class, id);
        dynamoDBMapper.delete(customer);
        return "Customer "+id+" deleted";
    }
    public List<Customer> getAllCustomers(){
        return dynamoDBMapper.scan(Customer.class, new DynamoDBScanExpression());
    }
    public String updateCustomer(String id, Customer customer){
        dynamoDBMapper.save(customer,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return "Updated: "+id;
    }
}
