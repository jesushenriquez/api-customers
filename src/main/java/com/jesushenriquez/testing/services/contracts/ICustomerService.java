package com.jesushenriquez.testing.services.contracts;

import com.jesushenriquez.testing.dtos.requests.UserCreateRequest;
import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<CustomerEntity> createCustomer(UserCreateRequest userCreateRequest);

}
