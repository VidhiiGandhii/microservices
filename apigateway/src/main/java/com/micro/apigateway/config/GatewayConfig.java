package com.micro.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("projectservice", r -> r.path("/projects/**")
                .uri("lb://projectservice"))
            .route("castingservice", r -> r.path("/casting/**")
                .uri("lb://castingservice"))
            .route("budgetservice", r -> r.path("/budgets/**")
                .uri("lb://budgetservice"))
            .route("scheduleservice", r -> r.path("/schedule/**")
                .uri("lb://scheduleservice"))
            .build();
    }
}
