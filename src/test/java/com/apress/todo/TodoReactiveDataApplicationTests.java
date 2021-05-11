package com.apress.todo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TodoReactiveDataApplicationTests {

  @Test
  void contextLoads() {
  }

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void toDoTest() {
    String body = this.restTemplate.getForObject("/todo", String.class);
    assertThat(body).contains("Read a Book");
  }
}
