package com.nttdata.bootcamp.microservicio01.service.impl;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.repository.CustomerRepository;
import com.nttdata.bootcamp.microservicio01.service.CustomerService;
import com.nttdata.bootcamp.microservicio01.utils.constant.ErrorCode;
import com.nttdata.bootcamp.microservicio01.utils.exception.OperationNoCompletedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    return customerRepository.findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentity(customer.getDocumentIdentity().getNumber(), customer.getDocumentIdentity().getTypeDocumentIdentity())
            .switchIfEmpty(Mono.just(new Customer()))
            .filter(c -> c.getDocumentIdentity() == null)
            .doOnNext(s -> {
              BeanUtils.copyProperties(customer, s);
              s.setActive(true);
              s.getDocumentIdentity().setActive(true);
            })
            .flatMap(customerRepository::insert);
  }

  @Override
  public Mono<Customer> findById(String customerId) {
    log.info("Find by id a customer in the service.");
    return customerRepository.findById(customerId).switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.OPERATION_NO_COMPLETED.getCode(), ErrorCode.OPERATION_NO_COMPLETED.getMessage())));
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
            .switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.OPERATION_NO_COMPLETED.getCode(), ErrorCode.OPERATION_NO_COMPLETED.getMessage())));
  }

  @Override
  public Mono<Customer> remove(String customerId) {
    log.info("Delete a customer in the service.");
    return customerRepository.findById(customerId).switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.OPERATION_NO_COMPLETED.getCode(), ErrorCode.OPERATION_NO_COMPLETED.getMessage())))
            .doOnNext(p -> p.setActive(false))
            .flatMap(customerRepository::save);
  }
}
