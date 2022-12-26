package com.example.reactivedemo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@ConfigurationProperties("dynamo")
@Data
public class DynamoProperties {
    private URI endpointURI;
}
