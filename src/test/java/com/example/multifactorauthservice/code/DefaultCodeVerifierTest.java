package com.example.multifactorauthservice.code;

import com.example.multifactorauthservice.time.TimeProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultCodeVerifierTest {

    @Test
    public void testCodeIsValid() {
        String secret = "EX47GINFPBK5GNLYLILGD2H6ZLGJNNWB";
        long timeToRunAt = 1567975936;
        String correctCode = "862707";
        int timePeriod = 30;

        // allow for a -/+ ~30 second discrepancy
        assertTrue(isValidCode(secret, correctCode, timeToRunAt - timePeriod, timePeriod));
        assertTrue(isValidCode(secret, correctCode, timeToRunAt, timePeriod));
        assertTrue(isValidCode(secret, correctCode, timeToRunAt + timePeriod, timePeriod));

        // but no more
        assertFalse(isValidCode(secret, correctCode, timeToRunAt + timePeriod + 15, timePeriod));

        // test wrong code fails
        assertFalse(isValidCode(secret, "123", timeToRunAt, timePeriod));
    }

    private boolean isValidCode(String secret, String code, long time, int timePeriod) {
        TimeProvider timeProvider = mock(TimeProvider.class);
        when(timeProvider.getTime()).thenReturn(time);

        DefaultCodeVerifier verifier = new DefaultCodeVerifier(new DefaultCodeGenerator(), timeProvider);
        verifier.setTimePeriod(timePeriod);

        return verifier.isValidCode(secret, code);
    }

}