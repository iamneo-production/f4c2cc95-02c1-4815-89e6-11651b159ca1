package com.hackathon.apigateway.configuration;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;

@Configuration
public class CustomConfiguration {
	
	@Bean
	public RouteLocator customRouter(RouteLocatorBuilder builder)
	{
		return builder.routes().
				route(authenticationRoute->authenticationRoute.path("/authenticate/**").uri("lb://AUTHENTICATION-SERVICE")).
				route(adminRoute->adminRoute.path("/admin/**").uri("lb://ADMIN-SERVICE")).
				route(registerRoute->registerRoute.path("/registration/**").uri("lb://REGISTRATION-SERVICE")).
				route(gameplaymechanicsRoute->gameplaymechanicsRoute.path("/gameAPI/**").uri("lb://GAMEPLAY-MECHANICS-SERVICE")).
				route(leaderboardRoute->leaderboardRoute.path("/leaderboard/**").uri("lb://LEADERBOARD-SERVICE")).build();
	}

	@Bean
	public HttpClient httpClient() {

		return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
	}

}
