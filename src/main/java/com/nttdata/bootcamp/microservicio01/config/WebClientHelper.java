package com.nttdata.bootcamp.microservicio01.config;

import com.nttdata.bootcamp.microservicio01.model.dto.AccountDto;
import com.nttdata.bootcamp.microservicio01.model.dto.CreditDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class WebClientHelper {

  @Autowired private WebClient webClientAccount;

  @Autowired private WebClient webClientCredit;

  public Flux<AccountDto> findByIdCustomerAccount(String customerId) {
    log.info("Getting accouunt for customerId: [{}]", customerId);
    return webClientAccount
        .get()
        .uri(uriBuilder -> uriBuilder.path("v1/accounts/customer/" + customerId).build())
        .retrieve()
        .bodyToFlux(AccountDto.class)
        .onErrorResume(
            error -> {
              System.err.println("Error during call: " + error.getMessage());
              return Flux.empty();
            });
  }

  public Flux<CreditDto> findByIdCustomerCredit(String customerId) {
    log.info("Getting credit for customerId: [{}]", customerId);
    return webClientCredit
        .get()
        .uri(uriBuilder -> uriBuilder.path("v1/credits/customer/" + customerId).build())
        .retrieve()
        .bodyToFlux(CreditDto.class)
        .onErrorResume(
            error -> {
              System.err.println("Error during call: " + error.getMessage());
              return Flux.empty();
            });
  }
}
