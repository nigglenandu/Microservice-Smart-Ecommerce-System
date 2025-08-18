package com.example.gateway;

import com.example.gateway.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    public GatewaySecurityConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    // Register your JWT WebFilter
    @Bean
    public WebFilter jwtAuthenticationFilter() {
        return authenticationFilter;
    }

    // Reactive Security configuration
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/**").permitAll() // Let JWT filter handle authorization
                        .anyExchange().authenticated()
                );

        return http.build();
    }
}
