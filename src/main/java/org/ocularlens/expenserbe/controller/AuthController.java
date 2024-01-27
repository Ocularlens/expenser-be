package org.ocularlens.expenserbe.controller;

import jakarta.validation.Valid;
import org.ocularlens.expenserbe.requests.LoginRequest;
import org.ocularlens.expenserbe.requests.RegisterRequest;
import org.ocularlens.expenserbe.response.JWTResponse;
import org.ocularlens.expenserbe.serviceimpl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public JWTResponse login(@Valid@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.username(), loginRequest.password());
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterRequest> register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.newUser(
                registerRequest.firstname(),
                registerRequest.lastname(),
                registerRequest.username(),
                registerRequest.password(),
                registerRequest.role()
        );

        return ResponseEntity.ok().build();
    }
}