package com.example.multifactorauthservice.resource;

import com.example.multifactorauthservice.exception.CodeGenerationException;
import com.example.multifactorauthservice.usecase.GenerateCodeUseCase;
import com.example.multifactorauthservice.usecase.ValidateCodeUseCase;
import lombok.RequiredArgsConstructor;
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

    @Override
    public boolean validateCode(String code) {
        return validateCodeUseCase.isValid(code);
    }

}
