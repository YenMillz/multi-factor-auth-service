package com.example.multifactorauthservice.usecase;

import com.example.multifactorauthservice.code.CodeGenerator;
import com.example.multifactorauthservice.exception.CodeGenerationException;
import com.example.multifactorauthservice.mail.MailSender;
import com.example.multifactorauthservice.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateCodeUseCase {

    private final CodeGenerator codeGenerator;
    private final TimeProvider timeProvider;
    private final MailSender mailSender;

    @Value("${multiFactor.code.secret}")
    private String secret;

    public void generateCodeAndSendEmail(String email) throws CodeGenerationException {
        var time = this.timeProvider.getTime();
        var code = this.codeGenerator.generate(this.secret, time);
        this.mailSender.sendCode(email, "Generated OTP", code);
    }

}
