package com.example.resume_analyzer.resume.dto;

import com.example.resume_analyzer.resume.entity.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResumeResponse {
    private Long id;
//    private String fileImport;
    private String fileExport;
    private String type;
    private Long userId;
    public static ResumeResponse fromEntity(Resume resume){
        ResumeResponse response = ResumeResponse.builder()
                .id(resume.getId())
//                .fileImport(resume.getFileImport())
                .fileExport(resume.getFileExport())
                .type(resume.getType().toString())
                .userId(resume.getUser().getId())
                .build();
        return response;
    }
}
