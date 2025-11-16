package com.example.reservasBoscoMdv.config;

import com.example.reservasBoscoMdv.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration          //Archivo de configuración
@EnableWebSecurity      //Configuración de Spring Security
@EnableMethodSecurity  // Permite usar @PreAuthorize en controladores
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF (no necesario en APIs REST con JWT)
                .csrf(AbstractHttpConfigurer::disable)

                // Configurar autorización de peticiones HTTP
                // Define qué rutas son públicas y cuáles requieren autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/tramo-horario/list").hasRole("USER")
                        .requestMatchers("/tramo-horario/insert").hasRole("ADMIN")
                        .requestMatchers("/tramo-horario/delete/**").hasRole("ADMIN")
                        .requestMatchers("/tramo-horario/update/**").hasRole("ADMIN")
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/aula/list").hasRole("USER")
                        .requestMatchers("/aula/list/capacidad/**").hasRole("USER")
                        .requestMatchers("/aula/list/ordenadores").hasRole("USER")
                        .requestMatchers("/aula/list/no-ordenadores").hasRole("USER")
                        .requestMatchers("/aula/with-reservas/**").hasRole("USER")
                        .requestMatchers("/aula/delete/**").hasRole("ADMIN")
                        .requestMatchers("/aula/update/**").hasRole("ADMIN")
                        .requestMatchers("/aula/insert/**").hasRole("ADMIN")
                        .requestMatchers("/reserva/list").hasRole("USER")
                        .requestMatchers("/reserva/insert").hasRole("USER")
                        .requestMatchers("/reserva/delete/**").hasRole("USER")
                        .requestMatchers("/reserva/update/**").hasRole("USER")
                        .requestMatchers("/usuario/update/**").hasRole("USER")
                        .requestMatchers("/usuario/delete/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/list-email/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/list-name/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // Configurar validación automática de tokens JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())  // Cómo validar el token
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())  // Cómo extraer roles
                        )
                )

                // Sin sesiones (stateless) - cada petición debe llevar su token
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Configura cómo validar los tokens JWT con la clave secreta
        return NimbusJwtDecoder.withSecretKey(jwtService.getSecretKey()).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        // Configura cómo extraer los roles del token
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("roles");  // Buscar en claim "roles"
        authoritiesConverter.setAuthorityPrefix("ROLE_");             // Sin prefijo adicional

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return jwtConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt para cifrar contraseñas en la base de datos
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Necesario para validar email/password en el login
        return authConfig.getAuthenticationManager();
    }
}