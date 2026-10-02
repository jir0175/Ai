package com.aimentor.ai_mentor.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController {
    @GetMapping
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("all work");
    }
}
