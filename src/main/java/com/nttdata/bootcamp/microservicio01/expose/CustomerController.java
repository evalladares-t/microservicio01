package com.nttdata.bootcamp.microservicio01.expose;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("api/v1/customer")
public class CustomerController {

  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/list")
  public  Flux<Customer> findAll() {
    log.info("List all customers in the controller.");
    return customerService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Customer>> findbyId(@PathVariable("id") String id) {
    log.info("Find by id a customer in the controller.");
    return customerService.findById(id)
            .flatMap(customerCreate -> Mono.just(ResponseEntity.ok(customerCreate)))
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PostMapping({"", "/"})
  public Mono<ResponseEntity<Customer>> create(@RequestBody Customer customer) {
    log.info("Create a customer in the controller.");
    return customerService.create(customer)
            .flatMap(customerCreate -> Mono.just(ResponseEntity.ok(customerCreate)))
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PutMapping({"", "/"})
  public Mono<ResponseEntity<Customer>> update(@RequestBody Customer customer) {
    log.info("Update a customer in the controller.");
    return customerService.update(customer)
            .flatMap(customerUpdate -> Mono.just(ResponseEntity.ok(customerUpdate)))
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PatchMapping({"", "/"})
  public Mono<ResponseEntity<Customer>> change(@RequestBody Customer customer) {
    log.info("Change a customer in the controller.");
    return customerService.change(customer)
            .flatMap(customerUpdate -> Mono.just(ResponseEntity.ok(customerUpdate)))
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Customer>> delete(@PathVariable("id") String id) {
    log.info("Delete a customer in the controller.");
    return customerService.remove(id)
            .flatMap(customer -> Mono.just(ResponseEntity.ok(customer)))
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
