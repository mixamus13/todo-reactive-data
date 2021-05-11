package com.apress.todo.reactive;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static reactor.core.publisher.Mono.fromSupplier;

import com.apress.todo.domain.Task;
import com.apress.todo.repository.TaskRepository;
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
public class TaskHandler {

  private final TaskRepository repository;


  public Mono<ServerResponse> getToDo(ServerRequest request) {
    return findTaskById(request.pathVariable("id"));
  }


  private Mono<ServerResponse> findTaskById(String id) {
    Mono<Task> toDo = repository.findById(id);
    Mono<ServerResponse> notFound = notFound().build();
    return toDo.flatMap(t -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(fromValue(t)))
        .switchIfEmpty(notFound);
  }

  public Mono<ServerResponse> getAllTask(ServerRequest request) {
    Flux<Task> toDos = repository.findAll();
    return ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(toDos, Task.class);
  }

  public Mono<ServerResponse> createNewTask(ServerRequest request) {
    Mono<Task> toDo = request.bodyToMono(Task.class);
    return ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(fromPublisher(toDo.flatMap(this::saveTask), Task.class));
  }

  private Mono<Task> saveTask(Task task) {
    return fromSupplier(
        () -> {
          repository
              .save(task)
              .subscribe();
          return task;
        });
  }
}
