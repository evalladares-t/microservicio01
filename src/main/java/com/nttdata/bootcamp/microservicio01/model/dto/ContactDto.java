package com.nttdata.bootcamp.microservicio01.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

  private String email;
  private String phoneNumber;
  private String imeiNumber;
}
