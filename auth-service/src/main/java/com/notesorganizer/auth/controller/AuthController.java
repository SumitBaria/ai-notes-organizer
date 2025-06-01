package com.notesorganizer.auth.controller;

import com.notesorganizer.auth.service.UserService;
import com.notesorganizer.common.dtos.ApiResponse;
import com.notesorganizer.common.dtos.AuthRequest;
import com.notesorganizer.common.dtos.AuthResponse;
import com.notesorganizer.common.dtos.AuthenticationRequest;
import com.notesorganizer.common.dtos.AuthenticationResponse;
import com.notesorganizer.common.dtos.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Handles login, logout, token validation")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "register", description = "Register User")
    public ResponseEntity<ApiResponse<Object>> registerUser(@RequestBody @Valid RegisterRequest request) {
        userService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse
                        .builder()
                        .success(true)
                        .build());
    }

    @PostMapping("/login")
    @Operation(summary = "login", description = "Authenticate user and return Jwt")
    public ResponseEntity<ApiResponse<AuthResponse>> loginUser(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("User Successfully LoggedIn")
                        .data(userService.login(request))
                        .build());
    }

    @PostMapping("/authenticate")
    @Operation(summary = "authenticate", description = "Validate Jwt token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticateUser(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .success(true)
                        .data(userService.authenticate(request))
                        .build());
    }

}
