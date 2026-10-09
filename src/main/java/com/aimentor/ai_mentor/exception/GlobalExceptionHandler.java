package com.aimentor.ai_mentor.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.aimentor.ai_mentor.learning_goal.exception.GoalAccessException;
import com.aimentor.ai_mentor.learning_goal.exception.GoalNotFoundException;
import com.aimentor.ai_mentor.user.exception.EmailAlreadyExistsException;
import com.aimentor.ai_mentor.user.exception.InvalidCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExist(EmailAlreadyExistsException ex){
        return ResponseEntity.status(409).body("email already exists");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex){
        return ResponseEntity.status(401).body("login or password not correct");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValid(MethodArgumentNotValidException ex){
        
        Map<String,String> errors = new HashMap<>();
        for(int i = 0; i < ex.getBindingResult().getFieldErrors().size();i++){
            FieldError error = ex.getBindingResult().getFieldErrors().get(i);
            errors.put(error.getField(),error.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(GoalNotFoundException.class)
    public ResponseEntity<String> goalNotFound(GoalNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(GoalAccessException.class)
    public ResponseEntity<String> goalAccessException(GoalAccessException ex){
        return ResponseEntity.status(403).body(ex.getMessage());
    }
}
