package com.example.multifactorauthservice.time;

import com.example.multifactorauthservice.exception.TimeProviderException;
import org.springframework.stereotype.Component;

import java.time.Instant;


// Nu se folosește nicăieri, brat

// Update, iară voi cu Dependency Injection-ul vostru ... :(
@Component
public class SystemTimeProvider implements TimeProvider {

    @Override
    public long getTime() throws TimeProviderException {
        return Instant.now().getEpochSecond();
    }

}
