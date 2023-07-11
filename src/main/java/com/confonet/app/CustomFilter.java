package com.confonet.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class CustomFilter implements GlobalFilter {

	Logger logger = LoggerFactory.getLogger(CustomFilter.class);
	
	//  this will execute for all the request coming to the API Gateway from all the microservices
	// below filter method is pre filter for cross cutting
	// log a response/ do the authorization at one point
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		logger.info("Authorization = " + request.getHeaders().getFirst("Authorization"));
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			ServerHttpResponse response = exchange.getResponse();
			logger.info("Response status "+ response.getStatusCode());
		}));
	}
	
	// after .then it will be treated as Post Request for all the request coming from microservices

	
}
