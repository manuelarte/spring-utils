package com.github.manuelarte.exampledemo;

import org.springframework.http.HttpStatus;

@lombok.AllArgsConstructor
@lombok.Data
public class ErrorResponse {

    private HttpStatus status;

    private String message;

}
