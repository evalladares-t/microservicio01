package com.nttdata.bootcamp.microservicio01.config;

import com.nttdata.bootcamp.microservicio01.utils.exception.OperationNoCompletedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(OperationNoCompletedException.class)
  public Mono<ResponseEntity<Map<String, String>>> handleCustomException(OperationNoCompletedException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("errorCode", ex.getErrorCode());
    errorResponse.put("errorMessage", ex.getErrorMessage());
    return Mono.just(ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse));
  }


  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<Map<String, String>>> handleGenericException(Exception ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("errorCode", "500");
    errorResponse.put("errorMessage", "Internal Server Error");
    return Mono.just(ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse));
  }

}
