package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Getter
    private final SecretKey secretKey;

    public JwtService() {
        // Genera automáticamente una clave secreta segura de 256 bits
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Genera un token JWT para un usuario autenticado
    public String generateToken(Authentication authentication) {
        // Extraer los roles del usuario y convertirlos a String
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Obtener el usuario real del principal
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Construir el token JWT
        return Jwts.builder()
                .subject(usuario.getEmail())  // Email del usuario
                .issuer("gestion-centro-api")       // Quién emite el token
                .issuedAt(new Date())               // Cuándo se creó
                .expiration(new Date(
                        System.currentTimeMillis() + 86400000  // Expira en 24h
                ))
                .claim("id", usuario.getId()) // ID del usuario (email)
                .claim("roles", roles)              // Roles del usuario
                .signWith(secretKey)                // Firmar con clave secreta
                .compact();                         // Generar String del token
    }

}