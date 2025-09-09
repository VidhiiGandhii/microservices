package com.micro.budget;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.micro.budget", "com.micro.budget.config"})
public class BudgetServiceApplication 
{
    public static void main(String[] args) 
    {
        SpringApplication.run( BudgetServiceApplication.class, args);
    }
}