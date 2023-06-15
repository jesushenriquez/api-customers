package com.jesushenriquez.testing.services.implementations;

import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import com.jesushenriquez.testing.repositories.implementations.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class CustomerManagementServiceTest {

    @MockBean
    ICustomerRepository customerRepository;

    @Autowired
    CustomerManagementService customerManagementService;

    @Test
    void create_customer_successfully() {

        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsmith@gmail.com")
                .build();


        CustomerEntity customerEntity = CustomerEntity.builder()
                .firstName("John")
                .lastName("Smith")
                .email("johnsmith@gmail.com")
                .build();

        Mockito.when(customerRepository.save(Mockito.any(CustomerEntity.class)))
                .thenReturn(Mono.just(customerEntity));

        Mockito.when(customerRepository.existsByEmail(Mockito.anyString()))
                        .thenReturn(Mono.just(false));

        StepVerifier.create(customerManagementService.saveCustomer(customerCreateRequest))
                .expectNext(customerEntity)
                .verifyComplete();

    }

}