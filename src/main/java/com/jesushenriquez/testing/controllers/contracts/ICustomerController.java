package com.jesushenriquez.testing.controllers.contracts;

import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import org.springframework.http.ResponseEntity;

public interface ICustomerController {

    ResponseEntity<Object> createCustomer(CustomerCreateRequest customerCreateRequest);
}
