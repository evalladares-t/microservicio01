package com.nttdata.bootcamp.microservicio01.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountTypeDto {
  SAVING("001", "AHORRO"),
  CURRENT("002", "CORRIENTE"),
  FIXED_TERM("003", "PLAZO FIJO");

  private final String code;
  private final String description;
}
