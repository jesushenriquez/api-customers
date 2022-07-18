package com.jesushenriquez.testing.repositories.implementations;

import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ICustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {

    Mono<CustomerEntity> findByEmail(String email);
    Mono<Boolean> existsByEmail(String email);


}