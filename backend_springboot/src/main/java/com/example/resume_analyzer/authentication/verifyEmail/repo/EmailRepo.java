package com.example.resume_analyzer.authentication.verifyEmail.repo;

import com.example.resume_analyzer.authentication.verifyEmail.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepo extends JpaRepository<Email, Long> {
    boolean existsByEmail(String email);
    Optional<Email> findByEmail(String email);
}
