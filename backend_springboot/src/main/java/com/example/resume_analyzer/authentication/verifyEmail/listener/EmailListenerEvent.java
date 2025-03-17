package com.example.resume_analyzer.authentication.verifyEmail.listener;

import com.example.resume_analyzer.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class EmailListenerEvent implements ApplicationListener<EmailListener> {
    private final JavaMailSender mailSender;
    @Override
    public void onApplicationEvent(EmailListener event) {
        String subject = "Email verification for website";
        String name = "Luna Do";
        String content =
                "<p>Thank you for your registration with us<br>"+
                        "This is a verified code.</p>"+
                        "<b>"+ event.getCode()+"</b>"+
                        "Thank you so much ^^<br><br>"+
                        "Your sincerely, <br>Luna Do";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        try{
            messageHelper.setFrom("liendhhha140217@gmail.com", name);
            messageHelper.setTo(event.getEmail());
            messageHelper.setText(content, true);
            messageHelper.setSubject(subject);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to send email");
        }
    }
}
