package com.jesushenriquez.testing.services.implementations;

import com.jesushenriquez.testing.components.exceptions.CustomerNotFoundException;
import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import com.jesushenriquez.testing.repositories.entities.CustomerEntity;
import com.jesushenriquez.testing.repositories.implementations.ICustomerRepository;
import com.jesushenriquez.testing.services.contracts.ICustomerManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.jesushenriquez.testing.utils.constants.CustomerResponseMessagesConstants.CUSTOMER_ALREADY_EXISTS_MESSAGE;
import static com.jesushenriquez.testing.utils.constants.CustomerResponseMessagesConstants.CUSTOMER_NOT_FOUND_MESSAGE;

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
                        return Mono.error(new CustomerNotFoundException(String.format(CUSTOMER_ALREADY_EXISTS_MESSAGE, customerCreateRequest.getEmail())));
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
    public Mono<CustomerEntity> updateCustomer(String email, CustomerCreateRequest customerCreateRequest) {
        return findCustomer(email)
                .flatMap(result -> {
                    if (Objects.isNull(result)) {
                        return Mono.error(new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND_MESSAGE, customerCreateRequest.getEmail())));
                    }

                    result.setFirstName(customerCreateRequest.getFirstName());
                    result.setLastName(customerCreateRequest.getLastName());
                    result.setEmail(customerCreateRequest.getEmail());

                    return customerRepository.save(result)
                            .doOnSuccess(response -> log.debug("Successful customer update"))
                            .doOnError(error -> log.debug("Error while trying update customer"));
                });
    }

    @Override
    public Mono<CustomerEntity> findCustomer(String email) {
        return customerRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND_MESSAGE, email))))
                .doOnSuccess(response -> log.debug("Customer found"))
                .doOnError(error -> log.debug("Customer not found"));
    }

    private Mono<Boolean> isCustomerExist(String email) {
        return customerRepository.existsByEmail(email)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND_MESSAGE, email))))
                .doOnSuccess(response -> log.debug("Customer found"))
                .doOnError(error -> log.debug("Customer not found"));
    }

    @Override
    public Mono<Void> deleteCustomer(String email) {
        return isCustomerExist(email)
                .flatMap(result -> {
                    if (Boolean.TRUE.equals(result)) {
                        return customerRepository.deleteByEmail(email);
                    }

                    return Mono.error(new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND_MESSAGE, email)));
                })
                .then()
                .doOnSuccess(response -> log.debug("Customer deleted successfully"))
                .doOnError(error -> log.debug("Customer didn't delete"));
    }
}
