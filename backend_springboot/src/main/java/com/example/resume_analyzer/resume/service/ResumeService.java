package com.example.resume_analyzer.resume.service;

import com.example.resume_analyzer.authentication.user.UserComponent;
import com.example.resume_analyzer.authentication.user.entity.UserEntity;
import com.example.resume_analyzer.exception.CustomException;
import com.example.resume_analyzer.resume.dto.ResumeResponse;
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

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepo resumeRepo;
    private final UserComponent userComponent;
//    public ResumeResponse uploadFile(MultipartFile file, String type){
//        if (file.isEmpty())
//            throw new CustomException(HttpStatus.BAD_REQUEST, "Please upload file");
//        UserEntity user = userComponent.userLogin();
//        String directory = "resume/"+ user.getId() + "/";
//        try {
//            Files.createDirectories(Path.of(directory));
//        } catch(IOException e){
//            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to upload file");
//        }
//        String path = directory + type + ".pdf";
//
//    }

    public String extractText(MultipartFile file){
        BodyContentHandler handler = new BodyContentHandler();
        try{
            Metadata metadata = new Metadata();
            InputStream inputStream = file.getInputStream();
            Parser parser = new AutoDetectParser();
            parser.parse(inputStream, handler, metadata, new ParseContext());
        } catch (IOException | TikaException | SAXException e){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Fail to extract text");
        }
        return handler.toString();
    }
}
