package com.example.gateway;

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

    // Define route-to-role mappings
    private final Map<String, String> routeRoleMap = Map.of(
            "/api/admin", "ROLE_ADMIN",
            "/api/user", "ROLE_USER",
            "/api/cart", "ROLE_USER"
    );

    public AuthenticationFilter(JwtUtilsWebFlux jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = jwtUtils.getJwtFromHeader(exchange.getRequest());
        String path = exchange.getRequest().getPath().value();

        System.out.println("🔐 Incoming request to: " + path);
        System.out.println("🔐 Token: " + token);

        if (token != null && jwtUtils.validateJwtToken(token)) {
            String roles = jwtUtils.getRolesFromJwtToken(token);
            System.out.println("🔐 Roles from token: " + roles);

            // Check if route requires a specific role
            for (Map.Entry<String, String> entry : routeRoleMap.entrySet()) {
                if (path.startsWith(entry.getKey())) {
                    String requiredRole = entry.getValue();
                    if (!roles.contains(requiredRole)) {
                        System.out.println("🚫 Access denied: missing role " + requiredRole);
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                }
            }

            // Pass roles downstream if needed
            exchange.getAttributes().put("roles", roles);

            // Proceed to next filter or service
            return chain.filter(exchange);
        }

        System.out.println("🚫 Unauthorized: missing or invalid token");
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}