package com.nttdata.bootcamp.microservicio01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum  CustomerType {

  PERSONAL("personal", true),
  BUSINESS("business", true);

  private final String description;
  private final boolean isActive;
}
