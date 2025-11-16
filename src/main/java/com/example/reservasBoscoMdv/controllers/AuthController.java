package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.DTO.LoginRequest;
import com.example.reservasBoscoMdv.DTO.RegisterRequest;
import com.example.reservasBoscoMdv.entities.Usuario;
import com.example.reservasBoscoMdv.enums.Roles;
import com.example.reservasBoscoMdv.repositories.IUsuarioRepository;
import com.example.reservasBoscoMdv.services.AuthService;
import com.example.reservasBoscoMdv.services.CustomUserDetailsService;
import com.example.reservasBoscoMdv.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

}
