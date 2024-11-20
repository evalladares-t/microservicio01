package com.nttdata.bootcamp.microservicio01.model.dto;

import com.nttdata.bootcamp.microservicio01.model.Contact;
import com.nttdata.bootcamp.microservicio01.model.CustomerSubType;
import com.nttdata.bootcamp.microservicio01.model.CustomerType;
import com.nttdata.bootcamp.microservicio01.model.DocumentIdentity;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFullDto {

  private String id;
  private CustomerType customerType;
  private CustomerSubType customerSubType;
  private String firstName;
  private String lastName;
  private DocumentIdentity documentIdentity;
  private Contact contact;
  private LocalDate dateBirth;
  private char sex;
  private List<AccountDto> accountDto;
  private List<CreditDto> creditDto;
  private Boolean active;
}
