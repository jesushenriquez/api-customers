package com.jesushenriquez.testing.repositories.implementations;

import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {

    Flux<CustomerEntity> findByEmail(String email);

}