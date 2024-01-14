package com.example.multifactorauthservice.secret;

public interface SecretGenerator {

    /**
     * @return A random base64 encoded string to use as the shared secret/key between the server and the client.
     */
    String generate();

}
