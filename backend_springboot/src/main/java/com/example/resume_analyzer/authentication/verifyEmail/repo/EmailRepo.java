package com.example.resume_analyzer.authentication.verifyEmail.repo;

import com.example.resume_analyzer.authentication.verifyEmail.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepo extends JpaRepository<Email, String> {
    boolean existsByEmail(String email);
}
