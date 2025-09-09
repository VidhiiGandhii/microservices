package com.micro.cast;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.micro.cast")


public class CastingServiceApplication 
{
    public static void main(String[] args) 
    {
        SpringApplication.run( CastingServiceApplication.class, args);
    }
}