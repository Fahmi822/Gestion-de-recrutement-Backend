package com.example.gestionderecrutementbackend.service;

import com.example.gestionderecrutementbackend.dto.LoginRequest;
import com.example.gestionderecrutementbackend.dto.SignupRequest;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.model.Utilisateur;
import com.example.gestionderecrutementbackend.repository.UtilisateurRepository;
import com.example.gestionderecrutementbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;  // Assuming a repository to handle User DB interactions

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Signup for Candidat
    public String signup(SignupRequest signupRequest) {
        if (utilisateurRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // Create a new Candidat object
        Candidat candidat = new Candidat();
        candidat.setNom(signupRequest.getNom());
        candidat.setPrenom(signupRequest.getPrenom());
        candidat.setEmail(signupRequest.getEmail());
        candidat.setMotDePasse(passwordEncoder.encode(signupRequest.getMotDePasse()));  // Encrypt password

        // Set other fields (optional based on signup form)
        // candidat.setAdresse(...);
        // candidat.setCv(...);
        // etc.

        utilisateurRepository.save(candidat);  // Save the Candidat

        return "Candidat registered successfully.";
    }

    // Login for all users (Candidat, Admin, Recruteur)
    public String login(LoginRequest loginRequest) {
        Optional<Utilisateur> user = utilisateurRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(loginRequest.getMotDePasse(), user.get().getMotDePasse())) {
            throw new RuntimeException("Invalid credentials.");
        }

        // Generate JWT Token
        return jwtUtil.generateToken(user.get().getEmail());
    }
}

