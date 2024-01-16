package com.example.multifactorauthservice;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

@Component
@EnableScheduling
public class TOTPExample {

    private static final long TIME_STEP = 30; // TOTP time step in seconds

    // Function to generate a 6-digit TOTP value
    private static int generateTOTP(String secretKey) {
        long currentTime = Instant.now().getEpochSecond();
        long counter = currentTime / TIME_STEP;

        // Convert the counter to a big-endian byte array
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(counter);
        byte[] counterBytes = buffer.array();

        // Generate HMAC-SHA1 hash using the secret key and counter
        byte[] hmacResult = hmacSHA1(secretKey.getBytes(), counterBytes);

        // Truncate the hash and obtain a 6-digit TOTP value
        int offset = hmacResult[hmacResult.length - 1] & 0xF;
        int truncatedHash = ((hmacResult[offset] & 0x7F) << 24) |
                ((hmacResult[offset + 1] & 0xFF) << 16) |
                ((hmacResult[offset + 2] & 0xFF) << 8) |
                (hmacResult[offset + 3] & 0xFF);

        return truncatedHash % (int) Math.pow(10, 6);
    }

    // Function to validate a TOTP code
    public static boolean validateTOTP(String secretKey, String providedCode) {
        // Generate the expected TOTP code
        String expectedCode = generateTOTPX(secretKey);

        // Compare the provided code with the expected code
        return Objects.equals(providedCode, expectedCode);
    }

    // Function to compute HMAC-SHA1
    private static byte[] hmacSHA1(byte[] key, byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // If the key is longer than 64 bytes, use SHA-1 hash of the key
            if (key.length > 64) {
                key = md.digest(key);
            }

            byte[] keyPad = new byte[64];
            byte[] innerKeyPad = new byte[64];
            for (int i = 0; i < key.length; i++) {
                keyPad[i] = key[i];
                innerKeyPad[i] = (byte) (key[i] ^ 0x36);
            }

            md.reset();
            md.update(innerKeyPad);
            md.update(data);

            byte[] innerHash = md.digest();

            md.reset();
            md.update(keyPad);
            md.update(innerHash);

            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HMAC-SHA1 algorithm not available", e);
        }
    }

    // Function to calculate remaining seconds until the next TOTP code
    private static long remainingSecondsUntilNextCode() {
        long currentTime = Instant.now().getEpochSecond();
        long timeUntilNextCode = TIME_STEP - (currentTime % TIME_STEP);
        return timeUntilNextCode;
    }

    @Scheduled(fixedRate = 1000)
    public void generate() {
        // Replace this secret key with your own
        String secretKey = "your_secret_key_here";

        // Generate and display the TOTP value
        String totpCode = generateTOTPX(secretKey);
//        System.out.printf("Current TOTP Code: %06d\n", totpCode);

        long remainingSeconds = remainingSecondsUntilNextCode();
        System.out.println("Seconds until the next code: " + remainingSeconds);
    }

    // Function to generate a 6-digit TOTP value
    public static String generateTOTPX(String secretKey) {
        long timeStep = 30; // TOTP time step in seconds
        long currentTime = Instant.now().getEpochSecond();

        long counter = currentTime / timeStep;

        // Convert the counter to a big-endian byte array
        StringBuilder counterBytes = new StringBuilder();
        for (int i = 7; i >= 0; --i) {
            counterBytes.append((char) ((counter >> (8 * i)) & 0xFF));
        }

        // Generate XOR-based hash using the secret key and counter
        String hmacResult = customHash(secretKey, counterBytes.toString());

        // Truncate the hash and obtain a 6-digit TOTP value
        int truncatedHash = ((hmacResult.charAt(hmacResult.length() - 1) & 0xF) << 24) |
                ((hmacResult.charAt(hmacResult.length() - 4) & 0xFF) << 16) |
                ((hmacResult.charAt(hmacResult.length() - 3) & 0xFF) << 8) |
                (hmacResult.charAt(hmacResult.length() - 2) & 0xFF);

        return String.valueOf(truncatedHash % (int) Math.pow(10, 6));
    }

    public static String customHash(String key, String data) {
        int hash = 0;

        // Combine key and data
        String combinedString = key + data;

        // Simple custom hash calculation
        for (int i = 0; i < combinedString.length(); i++) {
            hash = (hash * 31) + combinedString.charAt(i);
        }

        // Convert the integer hash value to a string
        return String.valueOf(hash);
    }

    public static void main(String[] args) {
        // Replace this secret key with your own
        String secretKey = "your_secret_key_here";

        // Generate and display the TOTP value
        String totpCode = generateTOTPX(secretKey);
//        System.out.printf("Current TOTP Code: %06d\n", totpCode);

        // Calculate and display remaining seconds until the next code
        long remainingSeconds = remainingSecondsUntilNextCode();
        System.out.println("Seconds until the next code: " + remainingSeconds);

        // Example of TOTP validation
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the provided TOTP code: ");
        String providedCode = scanner.nextLine();

        if (validateTOTP(secretKey, providedCode)) {
            System.out.println("Validation successful! TOTP code is valid.");
        } else {
            System.out.println("Validation failed! TOTP code is not valid.");
        }
    }
}

