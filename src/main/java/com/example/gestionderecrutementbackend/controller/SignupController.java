package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.repository.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class SignupController {

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signupCandidat(@RequestBody Candidat candidat) {
        // Vérifier si l'email existe déjà
        Optional<Candidat> existingCandidat = Optional.ofNullable(candidatRepository.findByEmail(candidat.getEmail()));
        if (existingCandidat.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        // Chiffrer le mot de passe
        candidat.setMotDePasse(passwordEncoder.encode(candidat.getMotDePasse()));

        // Enregistrer le candidat dans la base de données
        try {
            candidatRepository.save(candidat);
            return ResponseEntity.ok("Candidate registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving the candidate");
        }
    }
}
