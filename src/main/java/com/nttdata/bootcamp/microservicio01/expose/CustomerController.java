package com.nttdata.bootcamp.microservicio01.expose;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("api/v1/customer")
public class CustomerController {

  /*private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }*/

  @Autowired
  private CustomerService customerService;

  @GetMapping("/{id}")
  public Mono<Customer> byId(@PathVariable("id") String id) {
    log.info("byId>>>>>");
    return customerService.findById(id);
  }
}
