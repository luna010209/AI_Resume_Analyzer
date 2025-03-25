package com.example.resume_analyzer.resume.service;

import com.example.resume_analyzer.authentication.user.UserComponent;
import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import com.example.resume_analyzer.exception.CustomException;
import com.example.resume_analyzer.resume.dto.ResumeResponse;
import com.example.resume_analyzer.resume.entity.Resume;
import com.example.resume_analyzer.resume.repo.ResumeRepo;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.expression.ParserContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepo resumeRepo;
    private final UserComponent userComponent;
    public ResumeResponse extractFile(MultipartFile file){
        if (file.isEmpty())
            throw new CustomException(HttpStatus.BAD_REQUEST, "Please upload file");
        UserEntity user = userComponent.userLogin();
        // Create directory to save file
        String directory = "resume/"+ user.getId() + "/";
        try {
            Files.createDirectories(Path.of(directory));
        } catch(IOException e){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to create directory");
        }
        String path = directory + "CV.txt";

        // Extract text from file
        BodyContentHandler handler = new BodyContentHandler();
        try{
            Metadata metadata = new Metadata();
            InputStream inputStream = file.getInputStream();
            Parser parser = new AutoDetectParser();
            parser.parse(inputStream, handler, metadata, new ParseContext());
            Files.write(Path.of(path), handler.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException | TikaException | SAXException e){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to extract text");
        }

        // Save path into db
        Resume resume = Resume.builder()
                .type(Resume.Type.CV)
                .fileExport(path)
                .user(user).build();
        return ResumeResponse.fromEntity(resumeRepo.save(resume));
    }

//    public String extractText(MultipartFile file){
//        BodyContentHandler handler = new BodyContentHandler();
//        try{
//            Metadata metadata = new Metadata();
//            InputStream inputStream = file.getInputStream();
//            Parser parser = new AutoDetectParser();
//            parser.parse(inputStream, handler, metadata, new ParseContext());
//        } catch (IOException | TikaException | SAXException e){
//            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to extract text");
//        }
//        return handler.toString();
//    }
}
