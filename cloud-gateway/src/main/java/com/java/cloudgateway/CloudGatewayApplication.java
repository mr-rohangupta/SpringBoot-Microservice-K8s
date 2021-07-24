package com.java.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableHystrix
public class CloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

    @Bean
    public FallbackProvider departmentFallbackProvider() {
        GenericFallbackProvider departmentFallbackProvider = new GenericFallbackProvider();
        departmentFallbackProvider.setRoute("DEPARTMENT-SERVICE");
        return departmentFallbackProvider;
    }

    @Bean
    public FallbackProvider userFallbackProvider() {
        GenericFallbackProvider userFallbackProvider = new GenericFallbackProvider();
        userFallbackProvider.setRoute("USER-SERVICE");
        userFallbackProvider.setRawStatusCode(200);
        userFallbackProvider.setStatusCode(HttpStatus.OK);
        userFallbackProvider.setResponseBody("We are little busy. Comeback After Sometime");
        return userFallbackProvider;
    }

}
