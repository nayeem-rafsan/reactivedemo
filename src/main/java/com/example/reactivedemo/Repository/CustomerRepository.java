package com.example.reactivedemo.Repository;

//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.reactivedemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
//import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class CustomerRepository {
//    @Autowired
//    private DynamoDBMapper dynamoDBMapper;

    //POST Method
//    public Customer save(Customer customer){
//        dynamoDBMapper.save(customer);
//        return customer;
//    }
    //GET Method by Id
//    public Customer getCustomerById(String id){
//        return dynamoDBMapper.load(Customer.class, id);
//    }
//    //DELETE Method
//    public String delete(String id){
//        Customer customer = dynamoDBMapper.load(Customer.class, id);
//        dynamoDBMapper.delete(customer);
//        return "Customer "+id+" deleted";
//    }
//    //GET Method all customers
//    public List<Customer> getAllCustomers(){
//        return dynamoDBMapper.scan(Customer.class, new DynamoDBScanExpression());
//    }
//    //PUT Method
//    public String updateCustomer(String id, Customer customer){
//        dynamoDBMapper.save(customer,
//                new DynamoDBSaveExpression()
//                        .withExpectedEntry("id",
//                                new ExpectedAttributeValue(
//                                        new AttributeValue().withS(id)
//                                )));
//        return "Updated: "+id;
//    }
//    @Autowired
    private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;
//    @Autowired
    private final DynamoDbAsyncTable<Customer> customerDynamoDbAsyncTable;
//    public CustomerRepository(DynamoDbAsyncTable<Customer> customerDynamoDbAsyncTable) {
//        this.customerDynamoDbAsyncTable = customerDynamoDbAsyncTable;
//    }
    public CustomerRepository(DynamoDbEnhancedAsyncClient asyncClient) {
        this.enhancedAsyncClient = asyncClient;
        this.customerDynamoDbAsyncTable = enhancedAsyncClient.table(Customer.class.getSimpleName(), TableSchema.fromBean(Customer.class));
//      code for creating dynamodb table
//        this.customerDynamoDbAsyncTable.createTable(builder -> builder
//                .provisionedThroughput(ProvisionedThroughput.builder()
//                                .readCapacityUnits(10L)
//                                .writeCapacityUnits(10L)
//                                .build()));
    }
    public CompletableFuture<Void> save(Customer customer){
        return customerDynamoDbAsyncTable.putItem(customer);
    }
    public CompletableFuture<Customer> getCustomerByID(String id) {
        return customerDynamoDbAsyncTable.getItem(getKeyBuild(id));
    }
    //GET ALL CUSTOMERS
    public PagePublisher<Customer> getAllCustomer() {

        return customerDynamoDbAsyncTable.scan();
    }
    //UPDATE
    public CompletableFuture<Customer> updateCustomer(Customer customer) {
        return customerDynamoDbAsyncTable.updateItem(customer);
    }

    //DELETE
    public CompletableFuture<Customer> deleteCustomerById(String id) {
        return customerDynamoDbAsyncTable.deleteItem(getKeyBuild(id));
    }
    //GET_ALL_ITEM
    private Key getKeyBuild(String id) {
        return Key.builder().partitionValue(id).build();
    }
}
