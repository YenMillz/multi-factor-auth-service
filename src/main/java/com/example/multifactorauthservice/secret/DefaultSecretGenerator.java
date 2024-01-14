package com.example.multifactorauthservice.secret;

import java.util.Base64;
import java.util.Random;

public class DefaultSecretGenerator implements SecretGenerator {

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Random random = new Random();
    private final int numCharacters;

    public DefaultSecretGenerator() {
        this.numCharacters = 64;
    }

    /**
     * @param numCharacters The number of characters the secret should consist of.
     */
    public DefaultSecretGenerator(final int numCharacters) {
        this.numCharacters = numCharacters;
    }

    @Override
    public String generate() {
        return new String(this.encoder.encode(getRandomBytes()));
    }

    private byte[] getRandomBytes() {
        // 6 bits per char in base64
        final var bytes = new byte[(this.numCharacters * 6) / 8];
        this.random.nextBytes(bytes);

        return bytes;
    }

}
