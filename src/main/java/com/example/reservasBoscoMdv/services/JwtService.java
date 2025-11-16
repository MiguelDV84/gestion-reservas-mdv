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
import java.util.List;
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

        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Convertir authorities a lista de strings sin prefijo
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.replace("ROLE_", ""))  // Convertir "ROLE_ADMIN" → "ADMIN"
                .toList();

        return Jwts.builder()
                .subject(usuario.getEmail())
                .issuer("gestion-centro-api")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .claim("id", usuario.getId())
                .claim("roles", roles)  // 🔥 ahora es LISTA, no STRING
                .signWith(secretKey)
                .compact();
    }


}