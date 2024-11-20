package com.nttdata.bootcamp.microservicio01.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

  private String id;
  private String accountNumber;
  private String customer;
  private AccountTypeDto accountType;
  private String currency;
  private BigDecimal amountAvailable;
  private Boolean active;
}
