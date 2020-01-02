package com.xiaoxiya.organizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * EnableEurekaClient 注解只适用于Eureka作为注册中心，用来发现eureka注册中心
 * EnableCircuitBreaker注解高数spring cloud你将为你的服务使用Hystrix
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class OrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationServiceApplication.class, args);
    }

}
