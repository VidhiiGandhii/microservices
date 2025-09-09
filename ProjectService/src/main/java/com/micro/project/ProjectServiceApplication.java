package com.micro.project;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.micro.project")
public class ProjectServiceApplication 
{
    public static void main(String[] args) 
    {
        SpringApplication.run( ProjectServiceApplication.class, args);
    }
}