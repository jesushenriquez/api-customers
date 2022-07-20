package com.jesushenriquez.testing.controllers.implementations;

import com.jesushenriquez.testing.components.exceptions.CustomerNotFoundException;
import com.jesushenriquez.testing.controllers.contracts.ICustomerController;
import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import com.jesushenriquez.testing.dtos.responses.commons.CommonResponse;
import com.jesushenriquez.testing.services.contracts.ICustomerManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        log.info(customerCreateRequest);

        return ResponseEntity.ok(
                customerManagementService.saveCustomer(customerCreateRequest)
                        .doOnSuccess(response -> log.info("Customer created successfully"))
                        .doOnError(error -> log.info("Error with customer create request"))
        );
    }

    @PutMapping(path = "/{email}")
    @Override
    public ResponseEntity<Object> updateCustomer(@PathVariable String email, @RequestBody CustomerCreateRequest customerCreateRequest) {
        log.info(customerCreateRequest);

        return ResponseEntity.ok(
                customerManagementService.updateCustomer(email, customerCreateRequest)
                        .doOnSuccess(response -> log.info("Customer updated successfully"))
                        .doOnError(error -> log.info("Error with customer update request"))
        );
    }

    @GetMapping(path = "/{email}")
    @Override
    public ResponseEntity<Object> findCustomer(@PathVariable String email) {
        return ResponseEntity.ok(
                customerManagementService.findCustomer(email)
                        .doOnSuccess(response -> log.info("Customer found successfully"))
                        .doOnError(error -> log.info("Error finding customer"))
        );
    }

    @DeleteMapping(path = "/{email}")
    @Override
    public ResponseEntity<Object> deleteCustomer(@PathVariable String email) {
        return ResponseEntity.ok(
                customerManagementService.deleteCustomer(email)
                        .thenReturn(CommonResponse.builder()
                                .message("Customer deleted")
                                .build())
                        .doOnSuccess(response -> log.info("Customer deleted successfully"))
                        .doOnError(error -> log.info("Error with customer delete request"))
        );
    }
}
