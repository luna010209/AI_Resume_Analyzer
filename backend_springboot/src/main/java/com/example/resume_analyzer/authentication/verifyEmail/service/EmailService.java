package com.example.resume_analyzer.authentication.verifyEmail.service;

import com.example.resume_analyzer.authentication.verifyEmail.dto.EmailRequest;
import com.example.resume_analyzer.authentication.verifyEmail.entity.Email;
import com.example.resume_analyzer.authentication.verifyEmail.listener.EmailListener;
import com.example.resume_analyzer.authentication.verifyEmail.listener.EmailListenerEvent;
import com.example.resume_analyzer.authentication.verifyEmail.repo.EmailRepo;
import com.example.resume_analyzer.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailListenerEvent listenerEvent;
    private final EmailRepo emailRepo;
    public String sendCode(EmailRequest request){
        if (emailRepo.existsByEmail(request.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists");
        SecureRandom random = new SecureRandom();
        Integer verifiedCode = 100000+random.nextInt(900000);
        Email email = new Email(request.getEmail(), verifiedCode);
        emailRepo.save(email);
        listenerEvent.onApplicationEvent(new EmailListener(request.getEmail(), verifiedCode));
        return "Please check your email to verify";
    }
}
