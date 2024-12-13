package com.nttdata.bootcamp.microservicio01.utils.mapper;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.model.dto.CustomerFullDto;

public class CustomerMapper {
  public static CustomerFullDto toDto(Customer customer) {
    CustomerFullDto customerDto = new CustomerFullDto();
    customerDto.setId(customer.getId());
    customerDto.setCustomerType(customer.getCustomerType());
    customerDto.setCustomerSubType(customer.getCustomerSubType());
    customerDto.setFirstName(customer.getFirstName());
    customerDto.setLastName(customer.getLastName());
    customerDto.setDocumentIdentity(customer.getDocumentIdentity());
    customerDto.setContact(customer.getContact());
    customerDto.setDateBirth(customer.getDateBirth());
    customerDto.setSex(customer.getSex());
    customerDto.setActive(customer.getActive());
    return customerDto;
  }
}
