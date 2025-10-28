package com.micro.apigateway.config;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthHeaderFactory {

    @Value("${projectservice.auth.username}")
    String _ProjectUsername;
    @Value("${projectservice.auth.password}")
    String _ProjectPassword;

    @Value("${budgetservice.auth.username}")
    String _BudgetUsername;
    @Value("${budgetservice.auth.password}")
    String _BudgetPassword;

    @Value("${castingservice.auth.username}")
    String _CastingUsername;
    @Value("${castingservice.auth.password}")
    String _CastingPassword;
    
    @Value("${schedulingservice.auth.username}")
    String _SchedulingUsername;
    @Value("${schedulingservice.auth.password}")
    String _SchedulingPassword;


    @Value("${apigateway.shared.secret}")
    String _SharedSecret;
    
    String BuildAuthHeader(String serviceName)
    {
        String username = "";
        String password = "";

       if (serviceName.equalsIgnoreCase("Projectservice")) {
    username = _ProjectUsername;
    password = _ProjectPassword;
} else if (serviceName.equalsIgnoreCase("Budgetservice")) {
    username = _BudgetUsername;
    password = _BudgetPassword;
} else if (serviceName.equalsIgnoreCase("Castingservice")) {
    username = _CastingUsername;
    password = _CastingPassword;
} else if (serviceName.equalsIgnoreCase("Schedulingservice")) {
    username = _SchedulingUsername;
    password = _SchedulingPassword;
}


        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    String getSharedSecret()
    {
        return _SharedSecret;
    }
}