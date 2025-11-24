package com.example.reservasBoscoMdv.config;

import com.example.reservasBoscoMdv.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        /*.requestMatchers("/auth/**").permitAll()
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
                        .requestMatchers("/admin/**").hasRole("ADMIN")*/
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(jwtService.getSecretKey()).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("roles");
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Object roles = jwt.getClaim("roles");

            if (roles instanceof String roleStr) {
                return List.of(new SimpleGrantedAuthority("ROLE_" + roleStr));
            }

            return authoritiesConverter.convert(jwt);
        });

        return jwtConverter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


}