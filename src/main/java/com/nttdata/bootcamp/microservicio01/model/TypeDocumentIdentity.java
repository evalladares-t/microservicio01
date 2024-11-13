package com.nttdata.bootcamp.microservicio01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TypeDocumentIdentity {


  DNI("DNI", true),
  CE("CE", true),
  PASSPORT("PASSPORT", true),
  RUC("RUC", true);

  private final String description;
  private final boolean isActive;

}
