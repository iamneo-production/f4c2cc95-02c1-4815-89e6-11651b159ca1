package com.hackathon.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {
	
	@Bean
	public RouteLocator customRouter(RouteLocatorBuilder builder)
	{
		return builder.routes().
				route(authenticationRoute->authenticationRoute.path("/authenticate/**").uri("lb://AUTHENTICATION-SERVICE")).
				route(adminRoute->adminRoute.path("/admin/**").uri("lb://ADMIN-SERVICE")).
<<<<<<< HEAD
				route(registerRoute->registerRoute.path("/regiseration/**").uri("lb://REGISTRATION-SERVICE")).
=======
				route(registerRoute->registerRoute.path("/registration/**").uri("lb://REGISTRATION-SERVICE")).
>>>>>>> 852b2e2919df74e3f4806812775e20b32e240c7e
				route(gameplaymechanicsRoute->gameplaymechanicsRoute.path("/game/**").uri("lb://GAMEPLAY-MECHANICS-SERVICE")).
				route(leaderboardRoute->leaderboardRoute.path("/leaderboard/**").uri("lb://LEADERBOARD-SERVICE")).build();
	}

}
