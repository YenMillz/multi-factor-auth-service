package com.example.multifactorauthservice.time;

import com.example.multifactorauthservice.exception.TimeProviderException;

public interface TimeProvider {

    /**
     * @return The number of seconds since Jan 1st 1970, 00:00:00 UTC.
     */
    long getTime() throws TimeProviderException;

}
