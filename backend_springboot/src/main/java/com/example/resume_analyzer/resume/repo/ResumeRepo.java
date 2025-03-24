package com.example.resume_analyzer.resume.repo;

import com.example.resume_analyzer.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepo extends JpaRepository<Resume, Long> {

}
