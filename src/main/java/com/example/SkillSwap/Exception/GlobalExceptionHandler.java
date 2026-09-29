package com.example.SkillSwap.Exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException ex) { return ResponseEntity.status(404).body(Map.of("message", ex.getMessage(), "status", 404)); }
    @ExceptionHandler({IllegalArgumentException.class, InsufficientCreditException.class})
    public ResponseEntity<Map<String,Object>> badRequest(RuntimeException ex) { return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage(), "status", 400)); }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String,Object>> missing(NoResourceFoundException ex) { return ResponseEntity.status(404).body(Map.of("message", "Resource not found", "status", 404)); }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,Object>> conflict(DataIntegrityViolationException ex) { return ResponseEntity.status(409).body(Map.of("message", "This record is in use and cannot be deleted or changed", "status", 409)); }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> general(Exception ex) { return ResponseEntity.status(500).body(Map.of("message", "Something went wrong", "status", 500)); }
}
