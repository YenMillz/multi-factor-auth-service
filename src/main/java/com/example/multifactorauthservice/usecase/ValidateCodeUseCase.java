package com.example.multifactorauthservice.usecase;

import com.example.multifactorauthservice.code.CodeVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateCodeUseCase {

    private final CodeVerifier codeVerifier;

    @Value("${multiFactor.code.secret}")
    private String secret;

    public boolean isValid(String code) {
        return this.codeVerifier.isValidCode(this.secret, code);
    }

}
