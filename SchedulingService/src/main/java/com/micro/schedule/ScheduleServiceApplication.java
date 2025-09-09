package com.micro.schedule;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.micro.schedule")
public class ScheduleServiceApplication 
{
    public static void main(String[] args) 
    {
        SpringApplication.run( ScheduleServiceApplication.class, args);
    }
}