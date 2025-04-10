package co.com.nequi.api.router;

import co.com.nequi.api.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(POST("/api/users/{id}"), userHandler::createUser)
                .andRoute(GET("/api/users"), userHandler::listAllUsers)
                .and(route(GET("/api/users/{id}"), userHandler::findUserById)
                        .and(route(GET("/api/users/byName/{name}"), userHandler::findUsersByName)));
    }
}
