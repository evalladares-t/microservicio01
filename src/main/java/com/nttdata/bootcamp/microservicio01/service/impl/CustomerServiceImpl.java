package com.nttdata.bootcamp.microservicio01.service.impl;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.service.CustomerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Override
  public Mono<Customer> create(Customer customer) {
    return null;
  }

  @Override
  public Mono<Customer> findById(String customerId) {
    return Mono.empty();
  }

  @Override
  public Flux<Customer> findAll() {
    return null;
  }

  @Override
  public Mono<Customer> update(Customer customer) {
    return null;
  }

  @Override
  public Mono<Customer> change(Customer customer) {
    return null;
  }

  @Override
  public Mono<Customer> remove(String customerId) {
    return null;
  }
}
