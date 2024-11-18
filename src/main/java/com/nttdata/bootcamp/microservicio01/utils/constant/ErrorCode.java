package com.nttdata.bootcamp.microservicio01.utils.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  DATA_NOT_FOUND("404", "Data not found"),
  INVALID_REQUEST("400", "Invalid request parameters"),
  CUSTOMER_NO_CREATED("404", "The customer was not created"),
  CUSTOMER_NO_UPDATE("404", "The customer was not update"),
  CUSTOMER_NO_DELETED("404", "The customer was not deleted"),
  OPERATION_NO_COMPLETED("404", "Operación no completada"),
  INTERNAL_SERVER_ERROR("500", "Internal server error"),
  SERVICE_UNAVAILABLE("503", "Service unavailable");

  private final String code;
  private final String message;
}
