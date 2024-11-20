package com.nttdata.bootcamp.microservicio01.utils.Mapper;

import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.model.dto.CustomerFullDto;

public class CustomerMapper {
  public static CustomerFullDto toDTO(Customer customer) {
    CustomerFullDto customerDTO = new CustomerFullDto();
    customerDTO.setId(customer.getId());
    customerDTO.setCustomerType(customer.getCustomerType());
    customerDTO.setCustomerSubType(customer.getCustomerSubType());
    customerDTO.setFirstName(customer.getFirstName());
    customerDTO.setLastName(customer.getLastName());
    customerDTO.setDocumentIdentity(customer.getDocumentIdentity());
    customerDTO.setContact(customer.getContact());
    customerDTO.setDateBirth(customer.getDateBirth());
    customerDTO.setSex(customer.getSex());
    customerDTO.setActive(customer.getActive());
    return customerDTO;
  }
}
