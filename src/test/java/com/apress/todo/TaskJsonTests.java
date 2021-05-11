package com.apress.todo;

import static org.assertj.core.api.Assertions.assertThat;

import com.apress.todo.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * @author max.dokuchaev
 */
@JsonTest
public class TaskJsonTests {

  @Autowired
  private JacksonTester<Task> json;

  @Test
  public void toDoSerializeTest() throws Exception {
    Task task = new Task("Read a Book");
    assertThat(this.json.write(task))
        .isEqualToJson("todo.json");
    assertThat(this.json.write(task))
        .hasJsonPathStringValue("@.description");
    assertThat(this.json.write(task))
        .extractingJsonPathStringValue("@.description")
        .isEqualTo("Read a Book");
  }

  @Test
  public void toDoDeserializeTest() throws Exception {
    String content = "{\"description\":\"Read a Book\",\"completed\": true }";
    assertThat(this.json.parse(content))
        .isEqualTo(new Task("Read a Book", true));
    assertThat(
        this.json.parseObject(content).getDescription())
        .isEqualTo("Read a Book");
  }
}

