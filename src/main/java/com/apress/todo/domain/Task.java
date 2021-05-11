package com.apress.todo.domain;

import static java.util.UUID.randomUUID;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author max.dokuchaev
 */

@Document
@Data
public class Task {

  @Id
  private String id;
  private String description;
  private LocalDateTime created;
  private LocalDateTime modified;
  private boolean completed;

  public Task() {
    this.id = randomUUID().toString();
    this.created = LocalDateTime.now();
    this.modified = LocalDateTime.now();
  }

  public Task(String description) {
    this();
    this.description = description;
  }

  public Task(String description, boolean completed) {
    this();
    this.description = description;
    this.completed = completed;
  }
}
