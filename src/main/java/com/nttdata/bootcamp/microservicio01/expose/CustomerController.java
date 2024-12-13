package com.nttdata.bootcamp.microservicio01.expose;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.model.dto.ClientP2p;
import com.nttdata.bootcamp.microservicio01.model.dto.CustomerFullDto;
import com.nttdata.bootcamp.microservicio01.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("api/v1/customers")
public class CustomerController {

  @Autowired private CustomerService customerService;

  @GetMapping({"", "/"})
  public Flux<Customer> findAll() {
    log.info("List all customers in the controller.");
    return customerService.findAll();
  }

  @GetMapping({"/{id}/", "/{id}"})
  public Mono<Customer> findById(@PathVariable("id") String id) {
    log.info("Find by id a customer in the controller.");
    return customerService.findById(id);
  }

  @PostMapping({"", "/"})
  public Mono<ResponseEntity<Customer>> create(@RequestBody Customer customer) {
    log.info("Create a customer in the controller.");
    return customerService
        .create(customer)
        .flatMap(customerCreate -> Mono.just(ResponseEntity.ok(customerCreate)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PutMapping({"/{id}/", "/{id}"})
  public Mono<ResponseEntity<Customer>> update(
      @RequestBody Customer customer, @PathVariable("id") String customerId) {
    log.info("Update a customer in the controller.");
    return customerService
        .update(customer, customerId)
        .flatMap(customerUpdate -> Mono.just(ResponseEntity.ok(customerUpdate)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PatchMapping({"/{id}/", "/{id}"})
  public Mono<ResponseEntity<Customer>> change(
      @RequestBody Customer customer, @PathVariable("id") String customerId) {
    log.info("Change a customer in the controller.");
    return customerService
        .change(customer, customerId)
        .flatMap(customerUpdate -> Mono.just(ResponseEntity.ok(customerUpdate)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @DeleteMapping({"/{id}/", "/{id}"})
  public Mono<ResponseEntity<Customer>> delete(@PathVariable("id") String id) {
    log.info("Delete a customer in the controller.");
    return customerService
        .remove(id)
        .flatMap(customer -> Mono.just(ResponseEntity.ok(customer)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @GetMapping({"/{id}/full/", "/{id}/full"})
  public Mono<ResponseEntity<CustomerFullDto>> findbyIdFull(@PathVariable("id") String id) {
    log.info("Find by id a customer in the controller.");
    return customerService
        .findByIdFull(id)
        .flatMap(customerCreate -> Mono.just(ResponseEntity.ok(customerCreate)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PostMapping({"/findClientP2p", "/findClientP2p/"})
  public Mono<Customer> findClientP2p(@RequestBody ClientP2p clientP2p) {
    log.info("Find by findClientP2p a customer in the controller.");
    return customerService.findByValidateP2p(clientP2p);
  }
}
