package com.nttdata.bootcamp.microservicio01.service;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

  Mono<Customer> findById(String customerId);

  Flux<Customer> findAll();

  Mono<Customer> create(Customer customer);

  Mono<Customer> update(Customer customer, String customerId);

  Mono<Customer> change(Customer customer, String customerId);

  Mono<Customer> remove(String customerId);

}
