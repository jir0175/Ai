package com.aimentor.ai_mentor.user;

import com.aimentor.ai_mentor.user.dto.CreateUserRequest;
import com.aimentor.ai_mentor.user.dto.ResponseOnCreateUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<ResponseOnCreateUser> createUser(@RequestBody @Valid CreateUserRequest newUser){

        return ResponseEntity.status(201).body(userService.createUser(newUser));
    }

}
