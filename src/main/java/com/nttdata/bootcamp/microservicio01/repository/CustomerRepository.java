package com.nttdata.bootcamp.microservicio01.repository;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.model.TypeDocumentIdentity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

  Mono<Customer> findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentity(
      String numberDocumentIdentity, TypeDocumentIdentity typeDocumentIdentity);

  Mono<Customer>
  findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentityAndContact_PhoneAndContact_Email(
          String numberDocumentIdentity,
          TypeDocumentIdentity typeDocumentIdentity,
          String phone,
          String email);
}
