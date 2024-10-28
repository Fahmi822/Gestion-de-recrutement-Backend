package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.dto.LoginRequest;
import com.example.gestionderecrutementbackend.dto.LoginResponse;
import com.example.gestionderecrutementbackend.model.Utilisateur;
import com.example.gestionderecrutementbackend.model.Admin;
import com.example.gestionderecrutementbackend.model.Recruteur;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.service.UserService;
import com.example.gestionderecrutementbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Récupérer l'utilisateur par email
        Optional<Utilisateur> utilisateurOptional = userService.getUserByEmail(loginRequest.getEmail());

        // Vérifier si l'utilisateur existe
        if (utilisateurOptional.isEmpty()) {
            return ResponseEntity.status(404).body(new LoginResponse("Utilisateur non trouvé", null));
        }

        Utilisateur utilisateur = utilisateurOptional.get(); // Extraire l'utilisateur

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(loginRequest.getMotDePasse(), utilisateur.getMotDePasse())) {
            return ResponseEntity.status(401).body(new LoginResponse("Identifiants incorrects", null));
        }

        // Générer le token JWT
        String token = jwtUtil.generateToken(utilisateur.getEmail());

        // Identifier le type d'utilisateur et retourner un message différent selon le type
        if (utilisateur instanceof Admin) {
            return ResponseEntity.ok(new LoginResponse("Connexion réussie - Admin", token));
        } else if (utilisateur instanceof Recruteur) {
            return ResponseEntity.ok(new LoginResponse("Connexion réussie - Recruteur", token));
        } else if (utilisateur instanceof Candidat) {
            return ResponseEntity.ok(new LoginResponse("Connexion réussie - Candidat", token));
        } else {
            return ResponseEntity.status(400).body(new LoginResponse("Type d'utilisateur inconnu", null));
        }
    }
}
