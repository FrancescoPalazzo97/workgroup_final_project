package com.final_project.workgroup_final_project.controllers;

import com.final_project.workgroup_final_project.exceptions.UserAlreadyExistsException;
import com.final_project.workgroup_final_project.models.records.ErrorResponse;
import com.final_project.workgroup_final_project.models.records.LoginRequest;
import com.final_project.workgroup_final_project.models.records.RegisterRequest;
import com.final_project.workgroup_final_project.services.AuthService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
        } catch (UserAlreadyExistsException ex) {
            return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (BadCredentialsException ex) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } catch (AuthenticationException ex) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(
                message,
                status.value(),
                LocalDateTime.now());

        return ResponseEntity.status(status).body(errorResponse);
    }
}
