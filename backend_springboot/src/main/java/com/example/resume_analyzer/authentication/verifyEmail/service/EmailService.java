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
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

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

    public String verifyCode(EmailRequest request){
        Email email = emailRepo.findByEmail(request.getEmail()).orElseThrow(
                ()-> new CustomException(HttpStatus.NOT_FOUND, "No exist email")
        );
        if (email.isSuccess())
            throw new CustomException(HttpStatus.BAD_REQUEST, "Your email was already verified");
        else if (email.getExpiration().before(Date.from(Instant.now())))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Expire to verify");
        else if (!email.getCode().equals(request.getCode()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Wrong verified code");
        email.setSuccess(true);
        emailRepo.save(email);
        return "Your email is verified successful!";
    }
}
