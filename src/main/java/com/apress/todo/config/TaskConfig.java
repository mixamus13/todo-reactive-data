package com.apress.todo.config;

import static com.mongodb.reactivestreams.client.MongoClients.create;
import static java.lang.System.out;

import com.apress.todo.domain.Task;
import com.apress.todo.repository.TaskRepository;
import com.mongodb.reactivestreams.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author max.dokuchaev
 */
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.apress.todo.repository")
@RequiredArgsConstructor
public class TaskConfig extends AbstractReactiveMongoConfiguration {

  private final Environment environment;

  @Override
  protected String getDatabaseName() {
    return "todos";
  }

  @Override
  @Bean
  @DependsOn("embeddedMongoServer")
  public MongoClient reactiveMongoClient() {
    int port = environment.getProperty("local.mongo.port", Integer.class);
    return create(String.format("mongodb://localhost:%d", port));
  }

  @Bean
  public CommandLineRunner insertAndView(TaskRepository repository, ApplicationContext context) {
    return args -> {
      repository.save(new Task("Do homework")).subscribe();
      repository.save(new Task("Workout in the mornings", true)).subscribe();
      repository.save(new Task("Make dinner tonight")).subscribe();
      repository.save(new Task("Clean the studio", true)).subscribe();
      repository.findAll().subscribe(out::println);
    };
  }
}
