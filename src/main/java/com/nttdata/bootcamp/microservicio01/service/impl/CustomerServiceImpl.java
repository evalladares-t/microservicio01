package com.nttdata.bootcamp.microservicio01.service.impl;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.repository.CustomerRepository;
import com.nttdata.bootcamp.microservicio01.service.CustomerService;
import com.nttdata.bootcamp.microservicio01.utils.constant.ErrorCode;
import com.nttdata.bootcamp.microservicio01.utils.exception.OperationNoCompletedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

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
    return customerRepository
            .findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentity(
                    customer.getDocumentIdentity().getNumber(),
                    customer.getDocumentIdentity().getTypeDocumentIdentity()
            )
            .switchIfEmpty(Mono.just(new Customer()))
            .filter(c -> c.getDocumentIdentity() == null)
            .switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.CUSTOMER_NO_CREATED.getCode(), ErrorCode.CUSTOMER_NO_CREATED.getMessage())))
            .doOnNext(existingCustomer -> {
              BeanUtils.copyProperties(customer, existingCustomer);
              existingCustomer.setActive(true);
              existingCustomer.getDocumentIdentity().setActive(true);
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
  public Mono<Customer> update(Customer customer, String customerId) {
    log.info("Update a customer in the service.");
    return customerRepository.findById(customerId)
            .flatMap(customerDB -> {
              customer.setId(customerDB.getId());
              return customerRepository.save(customer);
            })
            .switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.CUSTOMER_NO_UPDATE.getCode(), ErrorCode.CUSTOMER_NO_UPDATE.getMessage())));
  }

  @Override
  public Mono<Customer> change(Customer customer, String customerId) {
    log.info("Change a customer in the service.");
    return customerRepository.findById(customerId)
            .flatMap(entidadExistente -> {
              // Iterar sobre los campos del objeto entidadExistente
              Field[] fields = customer.getClass().getDeclaredFields();
              for (Field field : fields) {
                if ("id".equals(field.getName())) {
                  continue; // Saltar el campo 'id'
                }
                field.setAccessible(true); // Para acceder a campos privados
                try {
                  // Verificar si el valor del campo en entidadParcial no es null
                  Object value = field.get(customer);
                  if (value != null) {
                    // Actualizar el campo correspondiente en entidadExistente
                    ReflectionUtils.setField(field, entidadExistente, value);
                  }
                } catch (IllegalAccessException e) {
                  e.printStackTrace(); // Manejo de errores si hay problemas con la reflexión
                }
              }
              // Guardar la entidad modificada
              return customerRepository.save(entidadExistente);
            })
            .switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.CUSTOMER_NO_UPDATE.getCode(), ErrorCode.CUSTOMER_NO_UPDATE.getMessage())));
  }

  @Override
  public Mono<Customer> remove(String customerId) {
    log.info("Delete a customer in the service.");
    return customerRepository.findById(customerId)
            .switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.DATA_NOT_FOUND.getCode(), ErrorCode.DATA_NOT_FOUND.getMessage())))
            .filter(p -> p.getActive().equals(true))
            .switchIfEmpty(Mono.error(new OperationNoCompletedException(ErrorCode.CUSTOMER_NO_DELETED.getCode(), ErrorCode.CUSTOMER_NO_DELETED.getMessage())))
            .doOnNext(p -> p.setActive(false))
            .flatMap(customerRepository::save);
  }
}
