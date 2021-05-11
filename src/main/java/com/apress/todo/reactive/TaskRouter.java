package com.apress.todo.reactive;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author max.dokuchaev
 */
@Configuration
public class TaskRouter {

  @Bean
  public RouterFunction<ServerResponse> monoRouterFunction(TaskHandler taskHandler) {
    return route(GET("/todo/{id}").and(accept(APPLICATION_JSON)),
        taskHandler::getToDo)
        .andRoute(GET("/todo").and(accept(APPLICATION_JSON)),
            taskHandler::getAllTask)
        .andRoute(POST("/todo").and(accept(APPLICATION_JSON)),
            taskHandler::createNewTask);
  }
}
