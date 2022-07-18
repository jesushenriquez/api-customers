package com.jesushenriquez.testing.controllers.implementations;

import com.jesushenriquez.testing.controllers.contracts.ICustomerController;
import com.jesushenriquez.testing.dtos.requests.CustomerCreateRequest;
import com.jesushenriquez.testing.services.contracts.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.jesushenriquez.testing.utils.constants.CustomerProcessConstants.CUSTOMER_CONTROLLER_PATH;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(path = CUSTOMER_CONTROLLER_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class CustomerController implements ICustomerController {

    private final ICustomerService customerService;

    @PostMapping
    @Override
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        log.info(customerCreateRequest);

        return ResponseEntity.ok(
                customerService.createCustomer(customerCreateRequest)
                        .doOnSuccess(response -> log.info("User created successfully"))
                        .doOnError(error -> log.info("Error with user create request"))
        );
    }
}
