package com.example.demo2.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OtpRequest {
    private String email;
    private int otp;
}
