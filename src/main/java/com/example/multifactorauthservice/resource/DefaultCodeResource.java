package com.example.multifactorauthservice.resource;

import com.example.multifactorauthservice.TOTPExample;
import com.example.multifactorauthservice.exception.CodeGenerationException;
import com.example.multifactorauthservice.usecase.GenerateCodeUseCase;
import com.example.multifactorauthservice.usecase.ValidateCodeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DefaultCodeResource implements CodeResource {

    private final GenerateCodeUseCase generateCodeUseCase;
    private final ValidateCodeUseCase validateCodeUseCase;

    @Override
    public void generateCode(String email) {
        try {
            generateCodeUseCase.generateCodeAndSendEmail(email);
        } catch (CodeGenerationException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/code")
    public String generateOTP() {
        // Replace this secret key with your own
        String secretKey = "your_secret_key_here";

        // Generate and display the TOTP value
        String totpCode = TOTPExample.generateTOTPX(secretKey);
//        System.out.printf("Current TOTP Code: %06d\n", totpCode);
        return totpCode;
    }

    @GetMapping("/isValidCode")
    public boolean validate(@RequestParam String code) {
        String secretKey = "your_secret_key_here";
        return TOTPExample.validateTOTP(secretKey, code);
    }

    @Override
    public boolean validateCode(String code) {
        return validateCodeUseCase.isValid(code);
    }

}
