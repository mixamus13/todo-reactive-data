package com.apress.todo.repository;

import com.apress.todo.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author max.dokuchaev
 */
@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {

}
