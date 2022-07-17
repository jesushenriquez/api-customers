package com.jesushenriquez.testing.dtos.requests;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 8084395064601241532L;

    private String firstName;
    private String lastName;
    private String email;
}
