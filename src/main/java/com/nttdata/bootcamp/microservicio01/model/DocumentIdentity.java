package com.nttdata.bootcamp.microservicio01.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentIdentity {

  private String number;
  private TypeDocumentIdentity typeDocumentIdentity;
  private boolean active;

}
