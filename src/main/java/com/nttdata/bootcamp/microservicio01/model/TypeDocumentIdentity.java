package com.nttdata.bootcamp.microservicio01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeDocumentIdentity {
  DNI("DNI"),
  CE("CE"),
  PASSPORT("PASSPORT"),
  RUC("RUC");

  private final String description;
}
