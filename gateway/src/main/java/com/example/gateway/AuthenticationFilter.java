package com.example.gateway.filter;

import com.example.gateway.JwtUtilsWebFlux;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AuthenticationFilter implements WebFilter {

    private final JwtUtilsWebFlux jwtUtils;

    // Map routes to required roles
    private final Map<String, String> routeRoleMap = Map.of(
            "/api/admin", "ADMIN",
            "/api/user", "USER"
    );

    public AuthenticationFilter(JwtUtilsWebFlux jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Extract JWT from Authorization header
        String token = jwtUtils.getJwtFromHeader(exchange.getRequest());

        if (token != null && jwtUtils.validateJwtToken(token)) {
            // Get roles from token
            String roles = jwtUtils.getRolesFromJwtToken(token);
            String path = exchange.getRequest().getPath().toString();

            // Check if user has required role for this route
            for (Map.Entry<String, String> entry : routeRoleMap.entrySet()) {
                if (path.startsWith(entry.getKey()) && !roles.contains(entry.getValue())) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            }

            // Optionally pass roles downstream
            exchange.getAttributes().put("roles", roles);

            // Proceed to downstream service
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
