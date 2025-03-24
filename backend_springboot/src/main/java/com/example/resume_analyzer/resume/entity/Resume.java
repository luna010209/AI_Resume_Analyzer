package com.example.resume_analyzer.resume.entity;

import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileImport;
    private String fileExport;

    @Enumerated(EnumType.STRING)
    private Type type;
    public enum Type{
        CV, INTRODUCTION, OTHER
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserEntity user;
}
