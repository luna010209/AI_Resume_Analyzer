package com.example.resume_analyzer.authentication.user.repo;

import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
