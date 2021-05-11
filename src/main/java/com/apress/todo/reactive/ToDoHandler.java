package com.apress.todo.reactive;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static reactor.core.publisher.Mono.fromSupplier;

import com.apress.todo.domain.ToDo;
import com.apress.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author max.dokuchaev
 */
@Component
@RequiredArgsConstructor
public class ToDoHandler {

  private final ToDoRepository repository;


  public Mono<ServerResponse> getToDo(ServerRequest request) {
    return findById(request.pathVariable("id"));
  }

  private Mono<ServerResponse> findById(String id) {
    Mono<ToDo> toDo = repository.findById(id);
    Mono<ServerResponse> notFound = notFound().build();
    return toDo.flatMap(t -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(fromValue(t)))
        .switchIfEmpty(notFound);
  }

  public Mono<ServerResponse> getToDos(ServerRequest request) {
    Flux<ToDo> toDos = repository.findAll();
    return ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(toDos, ToDo.class);
  }

  public Mono<ServerResponse> newToDo(ServerRequest request) {
    Mono<ToDo> toDo = request.bodyToMono(ToDo.class);
    return ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(fromPublisher(toDo.flatMap(this::save), ToDo.class));
  }

  private Mono<ToDo> save(ToDo toDo) {
    return fromSupplier(
        () -> {
          repository
              .save(toDo)
              .subscribe();
          return toDo;
        });
  }
}
