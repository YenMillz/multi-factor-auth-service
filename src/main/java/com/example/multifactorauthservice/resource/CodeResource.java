package com.example.multifactorauthservice.resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1/codes")
public interface CodeResource {

    @PostMapping
    void generateCode(@RequestBody String email);

    @PostMapping("/isValid")
    boolean validateCode(@RequestBody String code);
}
