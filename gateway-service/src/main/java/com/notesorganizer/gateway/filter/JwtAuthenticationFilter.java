package com.notesorganizer.gateway.filter;

import com.notesorganizer.common.dtos.ApiResponse;
import com.notesorganizer.common.dtos.AuthenticationRequest;
import com.notesorganizer.common.dtos.AuthenticationResponse;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter, Ordered {

    private final WebClient.Builder webClientBuilder;

    private static final String AUTH_SERVICE_VALIDATE_URL = "lp://localhost:8081/auth/authenticate";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        return webClientBuilder.build()
                .post()
                .uri(AUTH_SERVICE_VALIDATE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(buildRequest(token, "Bearer"))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<AuthenticationResponse>>() {})
                .flatMap(response -> {
                    if(response.getData().isAuthenticated()) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                })
                .onErrorResume(ex -> {
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private AuthenticationRequest buildRequest(String token, String tokenType) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setAccessToken(token);
        request.setTokenType(tokenType);
        return request;
    }
}
