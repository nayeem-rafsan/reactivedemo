package com.example.reactivedemo;

//import com.amazonaws.client.builder.AwsClientBuilder;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

//import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
//import software.amazon.awssdk.services.dynamodb.model.*;
//import software.amazon.awssdk.services.s3.S3Client;


@SpringBootApplication
//@EnableConfigurationProperties(DynamoProperties.class)g
public class ReactivedemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactivedemoApplication.class, args);
    }

//    @Bean
//    DynamoDbClient dynamoDbClient(DynamoProperties dynamoProperties){
//        return apply(dynamoProperties, DynamoDbClient.builder()).build();
//    }
//    @Bean
//    S3Client s3DbClient(DynamoProperties dynamoProperties){
//        return apply(dynamoProperties, S3Client.builder()).build();
//    }
//    public <BuilderT extends AwsClientBuilder<BuilderT,ClientT>, ClientT> AwsClientBuilder<BuilderT, ClientT> apply(DynamoProperties dynamoProperties, AwsClientBuilder<BuilderT,ClientT> builder){
//        if(dynamoProperties.getEndpointURI()!=null){
//            builder.endpointOverride(dynamoProperties.getEndpointURI());
//        }
//        return builder;
//    }
//
//    @Bean
//    ApplicationRunner applicationRunner(DynamoDbClient dynamoDbClient){
//        return args-> {
//            CreateTableRequest createTableRequest = CreateTableRequest.builder()
//                    .tableName("Customer")
//                    .keySchema(KeySchemaElement
//                            .builder()
//                            .keyType(KeyType.HASH)
//                            .attributeName("id")
//                            .build())
//                    .attributeDefinitions(AttributeDefinition
//                            .builder()
//                            .attributeName("id")
//                            .attributeType(ScalarAttributeType.S)
//                            .build())
//                    .provisionedThroughput(ProvisionedThroughput
//                            .builder()
//                            .writeCapacityUnits(5L)
//                            .readCapacityUnits(5L)
//                            .build())
//                    .build();
////            dynamoDbClient.createTable(createTableRequest);
//            dynamoDbClient.listTables().tableNames().forEach(System.out::println);
//        };
//    }
}