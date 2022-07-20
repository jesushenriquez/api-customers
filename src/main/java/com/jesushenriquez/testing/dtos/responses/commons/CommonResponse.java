package com.jesushenriquez.testing.dtos.responses.commons;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -4064771399219628398L;

    private String message;

}
