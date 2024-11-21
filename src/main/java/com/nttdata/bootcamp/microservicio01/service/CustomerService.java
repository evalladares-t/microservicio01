package com.nttdata.bootcamp.microservicio01.service;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.model.dto.AccountDto;
import com.nttdata.bootcamp.microservicio01.model.dto.CustomerFullDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

  Mono<Customer> findById(String customerId);

  Mono<CustomerFullDto> findByIdFull(String customerId);

  Flux<Customer> findAll();

  Mono<Customer> create(Customer customer);

  Mono<Customer> update(Customer customer, String customerId);

  Mono<Customer> change(Customer customer, String customerId);

  Mono<Customer> remove(String customerId);

  Flux<AccountDto> findByIdCustomerAccount(String customerId);
}
