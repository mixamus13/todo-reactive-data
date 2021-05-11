package com.apress.todo.domain;

import static java.util.UUID.randomUUID;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author max.dokuchaev
 */

@Document
@Data
public class ToDo {

  @Id
  private String id;
  private String description;
  private LocalDateTime created;
  private LocalDateTime modified;
  private boolean completed;

  public ToDo() {
    this.id = randomUUID().toString();
    this.created = LocalDateTime.now();
    this.modified = LocalDateTime.now();
  }

  public ToDo(String description) {
    this();
    this.description = description;
  }

  public ToDo(String description, boolean completed) {
    this();
    this.description = description;
    this.completed = completed;
  }
}
