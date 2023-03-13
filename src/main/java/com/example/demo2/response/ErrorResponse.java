package com.example.demo2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int statusCode;
    private String errorMessage;

    public ErrorResponse(String message) {
        super();
        this.errorMessage = message;
    }
}
