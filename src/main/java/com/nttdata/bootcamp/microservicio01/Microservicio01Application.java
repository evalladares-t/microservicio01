package com.nttdata.bootcamp.microservicio01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class Microservicio01Application {

  private static final Logger log = LoggerFactory.getLogger(Microservicio01Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Microservicio01Application.class, args);
    log.info("Hello World");
  }
}
