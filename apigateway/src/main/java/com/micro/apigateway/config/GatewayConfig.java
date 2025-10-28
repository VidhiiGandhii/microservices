package com.micro.apigateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
@EnableWebFlux
public class GatewayConfig {

    // AuthHeaderFactory _AuthFactory;
    // GatewayConfig(AuthHeaderFactory authFactory) {
    // this._AuthFactory = new AuthFactory();
    // }

      @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().permitAll()
            )
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        return http.build();
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
             .route("projectservice", r -> r.path("/projects/**").uri("lb://projectservice"))
            .route("castingservice", r -> r.path("/casting/**")
                .uri("lb://castingservice"))
            .route("budgetservice", r -> r.path("/budgets/**")
                .uri("lb://budgetservice"))
            .route("scheduleservice", r -> r.path("/schedule/**")
                .uri("lb://scheduleservice"))
                .route("authservice", r -> r.path("/auth/**")
            .uri("lb://authservice"))
           
            .build();
    }
         
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

