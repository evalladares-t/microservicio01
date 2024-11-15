package com.nttdata.bootcamp.microservicio01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
public class Customer {

  @Id
  private String id = UUID.randomUUID().toString();
  private CustomerType customerType;
  private String firstName;
  private String lastName;
  private DocumentIdentity documentIdentity;
  private Contact contact;
  private Date dateBirth;
  private char sex;
  private Boolean active;

}
