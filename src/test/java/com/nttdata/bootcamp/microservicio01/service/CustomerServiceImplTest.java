package com.nttdata.bootcamp.microservicio01.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nttdata.bootcamp.microservicio01.config.WebClientHelper;
import com.nttdata.bootcamp.microservicio01.model.Contact;
import com.nttdata.bootcamp.microservicio01.model.Customer;
import com.nttdata.bootcamp.microservicio01.model.DocumentIdentity;
import com.nttdata.bootcamp.microservicio01.model.TypeDocumentIdentity;
import com.nttdata.bootcamp.microservicio01.model.dto.*;
import com.nttdata.bootcamp.microservicio01.repository.CustomerRepository;
import com.nttdata.bootcamp.microservicio01.service.impl.CustomerServiceImpl;
import com.nttdata.bootcamp.microservicio01.utils.constant.ErrorCode;
import com.nttdata.bootcamp.microservicio01.utils.exception.OperationNoCompletedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

  @Mock private CustomerRepository customerRepository;

  @Mock private WebClientHelper webClientHelper;

  @InjectMocks private CustomerServiceImpl customerServiceImpl;

  @Test
  void testCreateCustomer_Success() {

    Customer newCustomer = new Customer();
    DocumentIdentity docIdentity = new DocumentIdentity("12345678", TypeDocumentIdentity.DNI, true);
    newCustomer.setDocumentIdentity(docIdentity);
    newCustomer.setActive(true);

    when(customerRepository.findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentity(
            "12345678", TypeDocumentIdentity.DNI))
        .thenReturn(Mono.empty());
    when(customerRepository.insert(any(Customer.class))).thenReturn(Mono.just(newCustomer));

    StepVerifier.create(customerServiceImpl.create(newCustomer))
        .consumeNextWith(
            customer -> {
              System.out.println("Emitted Customer: " + customer);
              assertNotNull(customer);
              assertEquals("12345678", customer.getDocumentIdentity().getNumber());
              assertEquals(
                  TypeDocumentIdentity.DNI,
                  customer.getDocumentIdentity().getTypeDocumentIdentity());
              assertTrue(customer.getActive());
            })
        .verifyComplete();

    verify(customerRepository)
        .findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentity(
            "12345678", TypeDocumentIdentity.DNI);
    verify(customerRepository).insert(any(Customer.class));
  }

  @Test
  void testCreateCustomer_AlreadyExists() {

    Customer existingCustomer = new Customer();
    DocumentIdentity docIdentity = new DocumentIdentity("12345678", TypeDocumentIdentity.DNI, true);
    existingCustomer.setDocumentIdentity(docIdentity);

    Customer newCustomer = new Customer();
    newCustomer.setDocumentIdentity(docIdentity);

    when(customerRepository.findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentity(
            "12345678", TypeDocumentIdentity.DNI))
        .thenReturn(Mono.just(existingCustomer));

    StepVerifier.create(customerServiceImpl.create(newCustomer))
        .expectErrorMatches(
            throwable ->
                throwable instanceof OperationNoCompletedException
                    && ((OperationNoCompletedException) throwable)
                        .getErrorMessage()
                        .contains(ErrorCode.CUSTOMER_NO_CREATED.getMessage()))
        .verify();

    verify(customerRepository)
        .findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentity(
            "12345678", TypeDocumentIdentity.DNI);
    verify(customerRepository, never()).insert(any(Customer.class));
  }

  @Test
  void testFindCustomer_ById() {

    String expectedId = "1005a110-e593-4b35-ac2e-eb258192d831";
    Customer existingCustomer = new Customer();
    DocumentIdentity docIdentity = new DocumentIdentity("12345678", TypeDocumentIdentity.DNI, true);
    existingCustomer.setId(expectedId);
    existingCustomer.setDocumentIdentity(docIdentity);
    existingCustomer.setActive(true);

    when(customerRepository.findById(expectedId)).thenReturn(Mono.just(existingCustomer));

    StepVerifier.create(customerServiceImpl.findById(expectedId))
        .consumeNextWith(
            customer -> {
              System.out.println("Emitted Customer: " + customer);
              assertNotNull(customer);
              assertEquals(expectedId, customer.getId());
              assertEquals("12345678", customer.getDocumentIdentity().getNumber());
              assertEquals(
                  TypeDocumentIdentity.DNI,
                  customer.getDocumentIdentity().getTypeDocumentIdentity());
              assertTrue(customer.getActive());
            })
        .verifyComplete();

    verify(customerRepository).findById(expectedId);
  }

  @Test
  void testFindCustomer_Full() {

    String customerId = "1005a110-e593-4b35-ac2e-eb258192d831";

    Customer customer = new Customer();
    customer.setId(customerId);
    customer.setActive(true);

    AccountDto accountDto = new AccountDto();
    accountDto.setActive(true);

    CreditDto creditDto = new CreditDto();
    creditDto.setActive(true);

    when(customerRepository.findById(customerId)).thenReturn(Mono.just(customer));

    when(webClientHelper.findByIdCustomerAccount(customerId)).thenReturn(Flux.just(accountDto));

    when(webClientHelper.findByIdCustomerCredit(customerId)).thenReturn(Flux.just(creditDto));

    Mono<CustomerFullDto> result = customerServiceImpl.findByIdFull(customerId);

    StepVerifier.create(result)
        .consumeNextWith(
            customerFullDto -> {
              assertNotNull(customerFullDto);
              assertEquals(customerId, customerFullDto.getId());
              assertEquals(1, customerFullDto.getAccountDto().size());
              assertEquals(1, customerFullDto.getCreditDto().size());
            })
        .verifyComplete();

    verify(customerRepository).findById(customerId);
    verify(webClientHelper).findByIdCustomerAccount(customerId);
    verify(webClientHelper).findByIdCustomerCredit(customerId);
  }

  @Test
  void testFindCustomer_All() {

    Customer existingCustomer1 = new Customer();
    DocumentIdentity docIdentity1 =
        new DocumentIdentity("11111111", TypeDocumentIdentity.DNI, true);
    existingCustomer1.setDocumentIdentity(docIdentity1);
    existingCustomer1.setActive(true);

    Customer existingCustomer2 = new Customer();
    DocumentIdentity docIdentity2 =
        new DocumentIdentity("22222222", TypeDocumentIdentity.DNI, true);
    existingCustomer2.setDocumentIdentity(docIdentity2);
    existingCustomer2.setActive(true);

    Customer existingCustomer3 = new Customer();
    DocumentIdentity docIdentity3 =
        new DocumentIdentity("33333333", TypeDocumentIdentity.DNI, true);
    existingCustomer3.setDocumentIdentity(docIdentity3);
    existingCustomer3.setActive(true);

    when(customerRepository.findAll())
        .thenReturn(Flux.just(existingCustomer1, existingCustomer2, existingCustomer3));

    StepVerifier.create(customerServiceImpl.findAll())
        .expectNextMatches(
            customer -> customer.getDocumentIdentity().getNumber().equals("11111111"))
        .expectNextMatches(
            customer -> customer.getDocumentIdentity().getNumber().equals("22222222"))
        .expectNextMatches(
            customer -> customer.getDocumentIdentity().getNumber().equals("33333333"))
        .verifyComplete();

    verify(customerRepository).findAll();
  }

  @Test
  void testUpdateCustomer() {

    String customerId = "12345";
    Customer existingCustomer = new Customer();
    existingCustomer.setId(customerId);
    existingCustomer.setActive(true);
    DocumentIdentity existingDocIdentity =
        new DocumentIdentity("11111111", TypeDocumentIdentity.DNI, true);
    existingCustomer.setDocumentIdentity(existingDocIdentity);

    Customer updatedCustomer = new Customer();
    updatedCustomer.setActive(true);
    DocumentIdentity updatedDocIdentity =
        new DocumentIdentity("22222222", TypeDocumentIdentity.DNI, true);
    updatedCustomer.setDocumentIdentity(updatedDocIdentity);

    when(customerRepository.findById(customerId)).thenReturn(Mono.just(existingCustomer));
    when(customerRepository.save(any(Customer.class)))
        .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

    StepVerifier.create(customerServiceImpl.update(updatedCustomer, customerId))
        .assertNext(
            customer -> {
              assertNotNull(customer);
              assertEquals(customerId, customer.getId());
              assertEquals(
                  updatedDocIdentity.getNumber(), customer.getDocumentIdentity().getNumber());
              assertEquals(
                  updatedDocIdentity.getTypeDocumentIdentity(),
                  customer.getDocumentIdentity().getTypeDocumentIdentity());
              assertTrue(customer.getActive());
            })
        .verifyComplete();

    verify(customerRepository).findById(customerId);
    verify(customerRepository).save(updatedCustomer);
  }

  @Test
  void testChangeCustomer() {

    String customerId = "12345";

    Customer existingCustomer = new Customer();
    existingCustomer.setId(customerId);
    existingCustomer.setActive(true);
    DocumentIdentity existingDocIdentity =
        new DocumentIdentity("11111111", TypeDocumentIdentity.DNI, true);
    existingCustomer.setDocumentIdentity(existingDocIdentity);

    Customer partialCustomer = new Customer();
    partialCustomer.setActive(false);
    DocumentIdentity updatedDocIdentity =
        new DocumentIdentity("22222222", TypeDocumentIdentity.PASSPORT, true);
    partialCustomer.setDocumentIdentity(updatedDocIdentity);

    when(customerRepository.findById(customerId)).thenReturn(Mono.just(existingCustomer));
    when(customerRepository.save(any(Customer.class)))
        .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

    StepVerifier.create(customerServiceImpl.change(partialCustomer, customerId))
        .assertNext(
            updatedCustomer -> {
              assertNotNull(updatedCustomer);
              assertEquals(customerId, updatedCustomer.getId());
              assertEquals(false, updatedCustomer.getActive()); // Se actualizó
              assertEquals("22222222", updatedCustomer.getDocumentIdentity().getNumber());
              assertEquals(
                  TypeDocumentIdentity.PASSPORT,
                  updatedCustomer.getDocumentIdentity().getTypeDocumentIdentity());
            })
        .verifyComplete();

    verify(customerRepository).findById(customerId);
    verify(customerRepository).save(any(Customer.class));
  }

  @Test
  void testRemove_Success() {

    String customerId = "1005a110-e593-4b35-ac2e-eb258192d831";
    Customer existingCustomer = new Customer();
    existingCustomer.setId(customerId);
    existingCustomer.setActive(true);

    when(customerRepository.findById(customerId)).thenReturn(Mono.just(existingCustomer));
    when(customerRepository.save(any(Customer.class)))
        .thenAnswer(
            invocation -> {
              Customer updatedCustomer = invocation.getArgument(0);
              updatedCustomer.setActive(false);
              return Mono.just(updatedCustomer);
            });

    StepVerifier.create(customerServiceImpl.remove(customerId))
        .consumeNextWith(
            customer -> {
              assertNotNull(customer);
              assertEquals(customerId, customer.getId());
              assertFalse(customer.getActive());
            })
        .verifyComplete();

    verify(customerRepository).findById(customerId);
    verify(customerRepository).save(any(Customer.class));
  }

  @Test
  void testRemove_CustomerNotFound() {

    String customerId = "nonexistent-id";

    when(customerRepository.findById(customerId)).thenReturn(Mono.empty());

    StepVerifier.create(customerServiceImpl.remove(customerId))
        .expectErrorMatches(
            throwable ->
                throwable instanceof OperationNoCompletedException
                    && ((OperationNoCompletedException) throwable)
                        .getErrorMessage()
                        .contains(ErrorCode.DATA_NOT_FOUND.getMessage()))
        .verify();

    verify(customerRepository).findById(customerId);
    verify(customerRepository, never()).save(any(Customer.class));
  }

  @Test
  void testRemove_CustomerNotActive() {

    String customerId = "1005a110-e593-4b35-ac2e-eb258192d831";
    Customer inactiveCustomer = new Customer();
    inactiveCustomer.setId(customerId);
    inactiveCustomer.setActive(false);

    when(customerRepository.findById(customerId)).thenReturn(Mono.just(inactiveCustomer));

    StepVerifier.create(customerServiceImpl.remove(customerId))
        .expectErrorMatches(
            throwable ->
                throwable instanceof OperationNoCompletedException
                    && ((OperationNoCompletedException) throwable)
                        .getErrorMessage()
                        .contains(ErrorCode.CUSTOMER_NO_DELETED.getMessage()))
        .verify();

    verify(customerRepository).findById(customerId);
    verify(customerRepository, never()).save(any(Customer.class));
  }

  @Test
  void testFindByValidateP2p_CustomerFound() {

    ClientP2p clientP2p = new ClientP2p();
    DocumentIdentity documentIdentity = new DocumentIdentity("12345678", TypeDocumentIdentity.DNI, true);
    ContactDto contact = new ContactDto("test@example.com", "987654321", "987654321");
    Contact expectedContact = new Contact(contact.getEmail(), null, contact.getPhoneNumber());
    clientP2p.setDocumentIdentity(documentIdentity);
    clientP2p.setContact(contact);

    Customer expectedCustomer = new Customer();
    expectedCustomer.setId("1005a110-e593-4b35-ac2e-eb258192d831");
    expectedCustomer.setDocumentIdentity(documentIdentity);
    expectedCustomer.setContact(expectedContact);
    expectedCustomer.setActive(true);

    when(customerRepository.findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentityAndContact_PhoneAndContact_Email(
            documentIdentity.getNumber(),
            documentIdentity.getTypeDocumentIdentity(),
            contact.getPhoneNumber(),
            contact.getEmail()))
            .thenReturn(Mono.just(expectedCustomer));

    StepVerifier.create(customerServiceImpl.findByValidateP2p(clientP2p))
            .consumeNextWith(customer -> {
              assertNotNull(customer);
              assertEquals(expectedCustomer.getId(), customer.getId());
              assertEquals(expectedCustomer.getDocumentIdentity(), customer.getDocumentIdentity());
              assertEquals(expectedCustomer.getContact(), customer.getContact());
              assertTrue(customer.getActive());
            })
            .verifyComplete();

    verify(customerRepository).findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentityAndContact_PhoneAndContact_Email(
            documentIdentity.getNumber(),
            documentIdentity.getTypeDocumentIdentity(),
            contact.getPhoneNumber(),
            contact.getEmail());
  }

  @Test
  void testFindByValidateP2p_CustomerNotFound() {

    ClientP2p clientP2p = new ClientP2p();
    DocumentIdentity documentIdentity = new DocumentIdentity("12345678", TypeDocumentIdentity.DNI, true);
    ContactDto contact = new ContactDto("test@example.com", "987654321", "987654321");
    clientP2p.setDocumentIdentity(documentIdentity);
    clientP2p.setContact(contact);

    when(customerRepository.findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentityAndContact_PhoneAndContact_Email(
            documentIdentity.getNumber(),
            documentIdentity.getTypeDocumentIdentity(),
            contact.getPhoneNumber(),
            contact.getEmail()))
            .thenReturn(Mono.empty());

    StepVerifier.create(customerServiceImpl.findByValidateP2p(clientP2p))
            .verifyComplete();

    verify(customerRepository).findByDocumentIdentity_NumberAndDocumentIdentity_TypeDocumentIdentityAndContact_PhoneAndContact_Email(
            documentIdentity.getNumber(),
            documentIdentity.getTypeDocumentIdentity(),
            contact.getPhoneNumber(),
            contact.getEmail());
  }
}
