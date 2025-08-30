package com.example.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class AuthenticationFilter implements WebFilter {

    private final JwtUtilsWebFlux jwtUtils;

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

        if ("true".equals(exchange.getRequest().getHeaders().getFirst("X-Gateway-Auth"))) {
            return chain.filter(exchange);
        }


        // Skip JWT check for public endpoints
        if (path.startsWith("/api/auth") || path.equals("/api/users/register")) {
            return chain.filter(exchange);
        }

        System.out.println("Incoming request to: " + path);
        System.out.println("Token: " + token);

        if (token != null && jwtUtils.validateJwtToken(token)) {
            List<String> roles = jwtUtils.getRolesFromJwtToken(token);
            System.out.println("Roles from token: " + roles);

            for (Map.Entry<String, String> entry : routeRoleMap.entrySet()) {
                if (path.startsWith(entry.getKey())) {
                    String requiredRole = entry.getValue();
                    if (!roles.contains(requiredRole)) {
                        System.out.println("Access denied: missing role " + requiredRole);
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return exchange.getResponse().setComplete();
                    }
                }
            }

            exchange.getAttributes().put("roles", roles);
            return chain.filter(exchange);
        }

        System.out.println("Unauthorized: missing or invalid token");
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
