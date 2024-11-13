package com.nttdata.bootcamp.microservicio01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Document(collection = "customer")
public class Customer {

  @Id
  private String id = UUID.randomUUID().toString();
  private String firstname;
  private String lastname;
  private String companyName;
  private String email;
  private Address address;
  private String telephone;
  private Date dateBirth;
  private char sex;
  private boolean isActive;
  private CustomerType customerType;
  private DocumentIdentity documentIdentity;
  private String username;

}
