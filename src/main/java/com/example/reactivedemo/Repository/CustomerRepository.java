package com.example.reactivedemo.Repository;

//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.reactivedemo.dto.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
//import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.ResponseBytes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final S3Client s3Client;
//    public CustomerRepository(DynamoDbAsyncTable<Customer> customerDynamoDbAsyncTable) {
//        this.customerDynamoDbAsyncTable = customerDynamoDbAsyncTable;
//    }
    public CustomerRepository(DynamoDbEnhancedAsyncClient asyncClient, S3Client s3Client) {
        this.enhancedAsyncClient = asyncClient;
        this.customerDynamoDbAsyncTable = enhancedAsyncClient.table(Customer.class.getSimpleName(), TableSchema.fromBean(Customer.class));
//        this.enhancedAsyncClient.list_table
//        this.customerDynamoDbAsyncTable.createTable(builder -> builder
//                .provisionedThroughput(ProvisionedThroughput.builder()
//                                .readCapacityUnits(10L)
//                                .writeCapacityUnits(10L)
//                                .build()));
        this.s3Client = s3Client;
        try {
            // if bucketname already exists it will throw an error
            this.createBucket("customerbucket");
        }
        catch (S3Exception s3Exception){
            System.out.println("bucket name already exists");
        }
        this.listAllBuckets();
        // defining bucketname and keyname to retrieve later
        String bucketName = "customerbucket";
        String keyName = "objectkey1";
        // create a object in bucket
//        this.putS3Object("customerbucket", "objectkey1");
        // get a objetct from bucket
        this.getObjectFromS3(bucketName,keyName);
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

    // create a bucket
    public void createBucket(String bucketName){
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();
        this.s3Client.createBucket(bucketRequest);
    }
    // list all the buckets
    public void listAllBuckets(){
        List<Bucket> buckets = this.s3Client.listBuckets().buckets();
        System.out.println("Your {S3} buckets are:");
        for (Bucket b : buckets) {
            System.out.println("* " + b.name());
        }
    }
    // put object in bucket
    public void putS3Object(String bucketName, String objectKey) {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                .metadata(metadata)
                    .build();
            this.s3Client.putObject(putOb, RequestBody.fromString(createDummyJsonData()));
        }
        catch(S3Exception s3Exception){
            System.out.println("S3 bucket put item error");
        }
    }
    // get object from bucket
    public void getObjectFromS3(String bucketName, String keyName) {

        try {
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .key(keyName)
                    .bucket(bucketName)
                    .build();
            ResponseInputStream<GetObjectResponse> object = this.s3Client.getObject(objectRequest);
            // convert responseInputStream to String and print
            BufferedReader reader = new BufferedReader(new InputStreamReader(object));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //helper function to generate dummy json file
    private String createDummyJsonData() {
        String response = "None";
        try {
            Map<String, String> elements = new HashMap<>();
            elements.put("name", "Nayeem Rafsan");
            elements.put("address", "Dhaka");
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.writeValueAsString(elements);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
