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
public class CreditDto {

  private String id;
  private String creditNumber;
  private String customerId;
  private CreditTypeDto creditType;
  private String currency;
  private BigDecimal creditLimit;
  private BigDecimal amountAvailable;
  private Boolean business;
  private Boolean active;
}
