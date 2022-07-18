package com.jesushenriquez.testing.services.implementations;

import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import com.jesushenriquez.testing.repositories.implementations.ICustomerRepository;
import com.jesushenriquez.testing.services.contracts.ICustomerManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@Service
public class CustomerManagementService implements ICustomerManagementService {

    private final ICustomerRepository customerRepository;

    @Override
    public Mono<CustomerEntity> saveCustomer(CustomerCreateRequest customerCreateRequest) {
        return isCustomerExist(customerCreateRequest.getEmail())
                .flatMap(result -> {
                    if (Boolean.TRUE.equals(result)) {
                        return Mono.error(new RuntimeException());
                    }

                    CustomerEntity user = CustomerEntity.builder()
                            .firstName(customerCreateRequest.getFirstName())
                            .lastName(customerCreateRequest.getLastName())
                            .email(customerCreateRequest.getEmail())
                            .build();
                    return customerRepository.save(user)
                            .doOnSuccess(response -> log.debug("Successful customer saving"))
                            .doOnError(error -> log.debug("Error while trying save customer"));
                });
    }

    @Override
    public Mono<CustomerEntity> findCustomer(CustomerCreateRequest customerCreateRequest) {
        return null;
    }

    private Mono<Boolean> isCustomerExist(String email) {
        return customerRepository.existsByEmail(email)
                .switchIfEmpty(Mono.just(false))
                .doOnSuccess(response -> log.debug("User found"))
                .doOnError(error -> log.debug("User not found"));
    }
}
