package com.example.multifactorauthservice.time;

import com.example.multifactorauthservice.exception.TimeProviderException;

import java.time.Instant;

public class SystemTimeProvider implements TimeProvider {

    @Override
    public long getTime() throws TimeProviderException {
        return Instant.now().getEpochSecond();
    }

}
