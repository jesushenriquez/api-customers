package com.jesushenriquez.testing.repositories.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMER")
public class CustomerEntity {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}