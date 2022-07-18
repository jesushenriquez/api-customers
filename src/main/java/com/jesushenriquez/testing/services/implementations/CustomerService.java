package com.jesushenriquez.testing.services.implementations;

import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import com.jesushenriquez.testing.services.contracts.ICustomerManagementService;
import com.jesushenriquez.testing.services.contracts.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerManagementService customerManagementService;

    @Override
    public Mono<CustomerEntity> createCustomer(CustomerCreateRequest customerCreateRequest) {

        return customerManagementService.saveCustomer(customerCreateRequest);
    }
}
