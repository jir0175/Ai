package com.aimentor.ai_mentor.user;

import com.aimentor.ai_mentor.user.dto.LoginRequest;
import com.aimentor.ai_mentor.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}