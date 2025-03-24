package com.example.resume_analyzer.authentication.user;

import com.example.resume_analyzer.authentication.user.dto.UserRequest;
import com.example.resume_analyzer.authentication.user.dto.UserResponse;
import com.example.resume_analyzer.authentication.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserResponse newUser(@RequestBody UserRequest request){
        return userService.createUser(request);
    }

    @GetMapping
    public UserResponse userLogin(){
        return userService.userLogin();
    }
}
