package com.example.resume_analyzer.authentication.verifyEmail;

import com.example.resume_analyzer.authentication.verifyEmail.dto.EmailRequest;
import com.example.resume_analyzer.authentication.verifyEmail.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {
    private final EmailService emailService;
    @PostMapping("sending")
    public String sendCode(@RequestBody EmailRequest request){
        return emailService.sendCode(request);
    }
}
