package com.jesushenriquez.testing.controllers.implementations;

import com.jesushenriquez.testing.components.exceptions.CustomerNotFoundException;
import com.jesushenriquez.testing.controllers.contracts.ICustomerController;
import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import com.jesushenriquez.testing.dtos.responses.generics.GenericResponse;
import com.jesushenriquez.testing.services.contracts.ICustomerManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.jesushenriquez.testing.utils.constants.CustomerProcessConstants.CUSTOMER_CONTROLLER_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(path = CUSTOMER_CONTROLLER_PATH, produces = APPLICATION_JSON_VALUE)
public class CustomerController implements ICustomerController {

    private final ICustomerManagementService customerManagementService;

    @PostMapping
    @Override
    public Mono<ResponseEntity<Object>> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        log.info(customerCreateRequest);

        return Mono.just(customerCreateRequest)
                .flatMap(customerManagementService::saveCustomer)
                .flatMap(customerEntity -> Mono.just(new ResponseEntity<Object>(customerEntity, HttpStatus.CREATED)))
                .doOnSuccess(response -> log.info("Customer created successfully"))
                .doOnError(error -> log.info("Error with customer create request"))
                .onErrorResume(CustomerNotFoundException.class, e -> Mono.just(new ResponseEntity<>(GenericResponse.<String>builder()
                        .data(e.getErrorMessage())
                        .build(), HttpStatus.BAD_REQUEST)));
    }

    @PutMapping(path = "/{email}")
    @Override
    public Mono<ResponseEntity<Object>> updateCustomer(@PathVariable String email, @RequestBody CustomerCreateRequest customerCreateRequest) {
        log.info(customerCreateRequest);

        return Mono.just(email)
                .flatMap(customerEmail -> customerManagementService.updateCustomer(customerEmail, customerCreateRequest))
                .flatMap(customerEntity -> Mono.just(new ResponseEntity<Object>(customerEntity, HttpStatus.OK)))
                .doOnSuccess(response -> log.info("Customer updated successfully"))
                .doOnError(error -> log.info("Error with customer update request"))
                .onErrorResume(CustomerNotFoundException.class, e -> Mono.just(new ResponseEntity<>(GenericResponse.<String>builder()
                        .data(e.getErrorMessage())
                        .build(), HttpStatus.BAD_REQUEST)));
    }

    @GetMapping(path = "/{email}")
    @Override
    public Mono<ResponseEntity<Object>> findCustomer(@PathVariable String email) {
        return Mono.just(email)
                .flatMap(customerEmail -> customerManagementService.findCustomer(email))
                .flatMap(customerEntity -> Mono.just(new ResponseEntity<Object>(customerEntity, HttpStatus.OK)))
                .doOnSuccess(response -> log.info("Customer found successfully"))
                .doOnError(error -> log.info("Error finding customer"))
                .onErrorResume(CustomerNotFoundException.class, e -> Mono.just(new ResponseEntity<>(GenericResponse.<String>builder()
                        .data(e.getErrorMessage())
                        .build(), HttpStatus.BAD_REQUEST)));
    }

    @DeleteMapping(path = "/{email}")
    @Override
    public Mono<ResponseEntity<Object>> deleteCustomer(@PathVariable String email) {
        return Mono.just(email)
                .flatMap(customerEmail -> customerManagementService.deleteCustomer(email))
                .thenReturn(new ResponseEntity<Object>(GenericResponse.<String>builder()
                        .data("Customer deleted")
                        .build(), HttpStatus.OK))
                .doOnSuccess(response -> log.info("Customer deleted successfully"))
                .doOnError(error -> log.info("Error with customer delete request"))
                .onErrorResume(CustomerNotFoundException.class, e -> Mono.just(new ResponseEntity<>(GenericResponse.<String>builder()
                        .data(e.getErrorMessage())
                        .build(), HttpStatus.BAD_REQUEST)));
    }
}
