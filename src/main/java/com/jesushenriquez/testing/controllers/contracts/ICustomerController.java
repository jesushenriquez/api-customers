package com.jesushenriquez.testing.controllers.contracts;

import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ICustomerController {

    Mono<ResponseEntity<Object>> createCustomer(CustomerCreateRequest customerCreateRequest);
    Mono<ResponseEntity<Object>> findCustomer(String email);
    Mono<ResponseEntity<Object>> updateCustomer(String email, CustomerCreateRequest customerCreateRequest);
    Mono<ResponseEntity<Object>> deleteCustomer(String email);
}
