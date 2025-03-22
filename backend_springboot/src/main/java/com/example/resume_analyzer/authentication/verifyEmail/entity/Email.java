package com.example.resume_analyzer.authentication.verifyEmail.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "verify_mail")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Integer code;
    private Date expiration;
    private boolean isSuccess;

    public Email(String email, Integer code){
        this.email= email;
        this.code= code;
        this.isSuccess = false;
        this.expiration = getCodeExpiration();
    }

    private Date getCodeExpiration(){
        Instant now = Instant.now();
        return Date.from(now.plusSeconds(60*10));
    }
}
