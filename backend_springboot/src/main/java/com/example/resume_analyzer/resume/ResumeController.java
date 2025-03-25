package com.example.resume_analyzer.resume;

import com.example.resume_analyzer.resume.dto.ResumeRequest;
import com.example.resume_analyzer.resume.dto.ResumeResponse;
import com.example.resume_analyzer.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("resume")
public class ResumeController {
    private final ResumeService resumeService;
    @PostMapping
    public ResumeResponse extractFile(@RequestParam MultipartFile file){
        return resumeService.extractFile(file);
    }
}
