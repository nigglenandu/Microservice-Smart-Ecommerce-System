package com.example.gateway;

import com.example.gateway.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class GatewaySecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    public GatewaySecurityConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public WebFilter jwtAuthenticationFilter() {
        return authenticationFilter;
    }
}
