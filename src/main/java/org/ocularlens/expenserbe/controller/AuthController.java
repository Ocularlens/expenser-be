package org.ocularlens.expenserbe.controller;

import jakarta.validation.Valid;
import org.ocularlens.expenserbe.common.Role;
import org.ocularlens.expenserbe.exception.UserNotFoundException;
import org.ocularlens.expenserbe.models.User;
import org.ocularlens.expenserbe.repository.UserRepository;
import org.ocularlens.expenserbe.requests.LoginRequest;
import org.ocularlens.expenserbe.requests.RegisterRequest;
import org.ocularlens.expenserbe.response.JWTResponse;
import org.ocularlens.expenserbe.serviceimpl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

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