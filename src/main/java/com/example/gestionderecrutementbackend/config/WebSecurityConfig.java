package com.example.gestionderecrutementbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors()  // Enable CORS
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/signup", "/api/auth/login","/api/auth/forgot-password","/api/auth/reset-password","/api/demandes/postuler","/api/demandes/accepted-entretien").permitAll()
                .requestMatchers("/api/offres/ajouter","/api/offres/toutes","/api/offres/supprimer/{id}","/api/offres/modifier/{id}","/api/offres/count","/api/demandes/{demandeId}/accepter","/api/demandes/{demandeId}/refuser").permitAll()
                .requestMatchers("/api/candidats","/api/candidats/{id}","/api/count", "/api/count/men","/api/count/women","/api/{id}/update-profile","/api/mail/send").permitAll()
                .requestMatchers("/api/demandes/{offreId}/demandes","/api/demandes/fichiers/{fileName}").permitAll()
                .requestMatchers("/api/demandes/fichiers/**").permitAll()
                .anyRequest().authenticated()  // Require authentication for other requests
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    // Global CORS configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")  // Allow Angular app
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

