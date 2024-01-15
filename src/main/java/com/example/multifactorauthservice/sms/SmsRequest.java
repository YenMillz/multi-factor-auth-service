package com.example.multifactorauthservice.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SmsRequest {
    @NotBlank
    private final String phoneNumber;
    @NotBlank
    private final String message;

    public SmsRequest(@JsonProperty("phoneNumber") String toNumber, @JsonProperty("message") String message) {
        this.phoneNumber = toNumber;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "toNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
