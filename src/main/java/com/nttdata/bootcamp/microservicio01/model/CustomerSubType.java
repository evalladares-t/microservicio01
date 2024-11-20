package com.nttdata.bootcamp.microservicio01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerSubType {
  REGULAR("REGULAR", true),
  VIP("VIP", true),
  MYPE("MYPE", true);

  private final String description;
  private final boolean isActive;
}
