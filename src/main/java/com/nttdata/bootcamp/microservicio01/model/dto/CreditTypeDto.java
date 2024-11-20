package com.nttdata.bootcamp.microservicio01.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreditTypeDto {
  PERSONAL("001", "PERSONAL"),
  BUSINESS("002", "BUSINESS"),
  CARD_BANK("003", "CARD_BANK");

  private final String code;
  private final String description;
}
