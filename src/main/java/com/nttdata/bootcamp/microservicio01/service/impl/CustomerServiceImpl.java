package com.nttdata.bootcamp.microservicio01.service.impl;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.repository.CustomerRepository;
import com.nttdata.bootcamp.microservicio01.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  private CustomerRepository customerRepository;

  public CustomerServiceImpl (CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }
  @Override
  public Mono<Customer> create(Customer customer) {
    log.info("Create a customer in the service.");
    return customerRepository.save(customer);
  }

  @Override
  public Mono<Customer> findById(String customerId) {
    log.info("Find by id a customer in the service.");
    return customerRepository.findById(customerId).switchIfEmpty(Mono.empty());
  }

  @Override
  public Flux<Customer> findAll() {
    log.info("List all customers in the service.");
    return customerRepository.findAll();
  }

  @Override
  public Mono<Customer> update(Customer customer) {
    log.info("Update a customer in the service.");
    return customerRepository.save(customer);
  }

  @Override
  public Mono<Customer> change(Customer customer) {
    log.info("Change a customer in the service.");
    return customerRepository.findById(customer.getId())
            .flatMap(customerDB -> {
              return create(customer);
            })
            .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<Customer> remove(String customerId) {
    log.info("Delete a customer in the service.");
    return customerRepository
            .findById(customerId)
            .flatMap(p -> customerRepository.deleteById(p.getId()).thenReturn(p));
  }
}
