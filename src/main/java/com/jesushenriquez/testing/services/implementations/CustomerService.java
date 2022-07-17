package com.jesushenriquez.testing.services.implementations;

import com.jesushenriquez.testing.dtos.requests.UserCreateRequest;
import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import com.jesushenriquez.testing.repositories.implementations.CustomerRepository;
import com.jesushenriquez.testing.services.contracts.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerEntity> createCustomer(UserCreateRequest userCreateRequest) {
        CustomerEntity user = CustomerEntity.builder()
                .firstName(userCreateRequest.getFirstName())
                .lastName(userCreateRequest.getLastName())
                .email(userCreateRequest.getEmail())
                .build();
        return customerRepository.save(user)
                .doOnSuccess(response -> log.info("Successful customer saving"))
                .doOnError(error -> log.info("Error while trying save customer"));
    }
}
