package com.jesushenriquez.testing.controllers.contracts;

import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import org.springframework.http.ResponseEntity;

public interface ICustomerController {

    ResponseEntity<Object> createCustomer(CustomerCreateRequest customerCreateRequest);
    ResponseEntity<Object> findCustomer(String email);
    ResponseEntity<Object> updateCustomer(String email, CustomerCreateRequest customerCreateRequest);
    ResponseEntity<Object> deleteCustomer(String email);
}
