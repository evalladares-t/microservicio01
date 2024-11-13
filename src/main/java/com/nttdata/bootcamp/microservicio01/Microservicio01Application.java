package com.nttdata.bootcamp.microservicio01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class Microservicio01Application {

  public static void main(String[] args) {
    SpringApplication.run(Microservicio01Application.class, args);
  }

}
