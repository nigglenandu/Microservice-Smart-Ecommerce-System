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
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/api/users/register").permitAll()
                        // Admin-wide endpoints
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/vendor/**").hasRole("VENDOR")
                        .pathMatchers("/api/delivery/**").hasRole("DELIVERY")
                        .pathMatchers("/api/support/**").hasRole("SUPPORT")
                        .anyExchange().permitAll()
                );

        return http.build();
    }
}
