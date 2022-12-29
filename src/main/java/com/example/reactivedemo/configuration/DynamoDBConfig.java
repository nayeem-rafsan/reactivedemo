package com.example.reactivedemo.configuration;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableDynamoDBRepositories
        (basePackages = "com.baeldung.spring.data.dynamodb.repositories")
public class DynamoDBConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB
                = new AmazonDynamoDBClient(amazonAWSCredentials());

        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }

        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(
                amazonAWSAccessKey, amazonAWSSecretKey);
    }
}

//
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//
//@Configuration
//public class DynamoDbConfiguration {
//    @Bean
//    public DynamoDBMapper dynamoDBMapper() {
//        return new DynamoDBMapper(buildAmazonDynamoDB());
//    }
//    private AmazonDynamoDB buildAmazonDynamoDB() {
//        return AmazonDynamoDBClientBuilder
//                .standard()
//                .withEndpointConfiguration(
//                        new AwsClientBuilder.EndpointConfiguration(
//                                "http://localhost:4566",
//                                ""
//                        )
//                )
//                .withCredentials(
//                        new AWSStaticCredentialsProvider(
//                                new BasicAWSCredentials(
//                                        "your_access_key_id",
//                                        "your_secret_access_key"
//                                )
//                        )
//                )
//                .build();
//    }
//
////    @Bean
////    public DynamoDBMapper dynamoDBMapper() {
////        return new DynamoDBMapper(buildAmazonDynamoDB());
////    }
////
////    private AmazonDynamoDB buildAmazonDynamoDB() {
////        return AmazonDynamoDBClientBuilder
////                .standard()
////                .withEndpointConfiguration(
////                        new AwsClientBuilder.EndpointConfiguration(
////                                "dynamodb.your-region.amazonaws.com",
////                                "your-region"
////                        )
////                )
////                .withCredentials(
////                        new AWSStaticCredentialsProvider(
////                                new BasicAWSCredentials(
////                                        "your-access-Key",
////                                        "your-secret-key"
////                                )
////                        )
////                )
////                .build();
////    }
//
//}
