package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.dto.LoginRequest;
import com.example.gestionderecrutementbackend.dto.LoginResponse;
import com.example.gestionderecrutementbackend.model.Utilisateur;
import com.example.gestionderecrutementbackend.model.Admin;
import com.example.gestionderecrutementbackend.model.Recruteur;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.service.AuthService;
import com.example.gestionderecrutementbackend.service.EmailService;
import com.example.gestionderecrutementbackend.service.UserService;
import com.example.gestionderecrutementbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Retrieve user by email
        Optional<Utilisateur> utilisateurOptional = userService.getUserByEmail(loginRequest.getEmail());
        if (utilisateurOptional.isEmpty()) {
            System.out.println("User not found with email: " + loginRequest.getEmail());
            return ResponseEntity.status(404).body(new LoginResponse("Utilisateur non trouvé", null, null, null));
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        System.out.println("User found: " + utilisateur.getEmail());

        // Verify password
        if (!passwordEncoder.matches(loginRequest.getMotDePasse(), utilisateur.getMotDePasse())) {
            System.out.println("Password mismatch for user: " + utilisateur.getEmail());
            return ResponseEntity.status(401).body(new LoginResponse("Identifiants incorrects", null, null, null));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(utilisateur.getEmail());
        System.out.println("Generated JWT token for user: " + utilisateur.getEmail());

        // Determine user role and ID
        String userRole;
        Long utilisateurId = utilisateur.getId(); // Récupérer l'ID de l'utilisateur
        if (utilisateur instanceof Admin) {
            userRole = "Admin";
        } else if (utilisateur instanceof Recruteur) {
            userRole = "Recruteur";
        } else if (utilisateur instanceof Candidat) {
            userRole = "Candidat";
        } else {
            System.out.println("Unknown user type for user: " + utilisateur.getEmail());
            return ResponseEntity.status(400).body(new LoginResponse("Type d'utilisateur inconnu", null, null, null));
        }

        System.out.println("User role: " + userRole);
        return ResponseEntity.ok(new LoginResponse("Connexion réussie - " + userRole, token, userRole, utilisateurId));
    }




}
