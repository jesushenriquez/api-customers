package com.jesushenriquez.testing.dtos.responses.generics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {

    @JsonProperty("data")
    private T data;
}
