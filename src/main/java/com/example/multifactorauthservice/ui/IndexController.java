package com.example.multifactorauthservice.ui;

import com.example.multifactorauthservice.TOTPExample;
import com.example.multifactorauthservice.code.CodeGenerator;
import com.example.multifactorauthservice.exception.CodeGenerationException;
import com.example.multifactorauthservice.mail.MailSender;
import com.example.multifactorauthservice.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final CodeGenerator codeGenerator;
    private final TimeProvider timeProvider;
    private final MailSender mailSender;

    @Value("${multiFactor.code.secret}")
    private String secret;

    @GetMapping("/")
    public String greetMessage(Model model) throws CodeGenerationException {
        var time = this.timeProvider.getTime();
        var code = this.codeGenerator.generate(this.secret, time);
        model.addAttribute("code", code);
        return "index";
    }

    @PostMapping("/")
    public String trimeteCod(Model model, @RequestBody MultiValueMap<String, String> formData) throws CodeGenerationException {
        var time = this.timeProvider.getTime();
        var code = this.codeGenerator.generate(this.secret, time);
        List<String> trimite = formData.get("trimite");
        if (trimite.get(0).equals("email")) {
            System.out.println("Metoda: email");
        } else if (trimite.get(0).equals("sms")) {
            System.out.println("Metoda: sms");
        }
        System.out.println("Cod: "+ formData.get("code").get(0));
        model.addAttribute("code", code);
        return "index";
    }

    @GetMapping("/code")
    @ResponseBody
    public Object jsonExample() throws CodeGenerationException {
        var time = this.timeProvider.getTime();
        var code = this.codeGenerator.generate(this.secret, time);
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        return response;
    }
}