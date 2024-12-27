package com.example.gestionderecrutementbackend.service;

import com.example.gestionderecrutementbackend.dto.LoginRequest;
import com.example.gestionderecrutementbackend.dto.SignupRequest;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.model.Utilisateur;
import com.example.gestionderecrutementbackend.repository.UtilisateurRepository;
import com.example.gestionderecrutementbackend.utils.JwtUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;  // Assuming a repository to handle User DB interactions

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    EmailService emailService;

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
    public String generatePasswordResetToken(String email) throws MessagingException {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé avec l'email : " + email);
        }

        Utilisateur utilisateur = utilisateurOptional.get();

        // Générer un token JWT ou un autre type de token
        String resetToken = jwtUtil.generateToken(utilisateur.getEmail());  // Utilisation du JWT

        // Créer le lien de réinitialisation contenant le token
        String resetLink = "http://localhost:4200/reset-password?resetToken=" + resetToken;

        // Appel du service d'email pour envoyer l'email avec le lien de réinitialisation
        emailService.sendResetPasswordEmail(email, resetLink);

        // Définir la date d'expiration pour le token (par exemple, 15 minutes)
        utilisateur.setResetToken(resetToken);
        utilisateur.setResetTokenExpiration(LocalDateTime.now().plusMinutes(15));
        utilisateurRepository.save(utilisateur);

        return resetToken;
    }


    public String resetPassword(String resetToken, String newPassword) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByResetToken(resetToken);
        if (utilisateurOptional.isEmpty()) {
            throw new RuntimeException("Jeton invalide ou expiré.");
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        if (utilisateur.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Le jeton a expiré.");
        }

        // Mettre à jour le mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(newPassword));
        utilisateur.setResetToken(null); // Invalider le jeton après utilisation
        utilisateur.setResetTokenExpiration(null);
        utilisateurRepository.save(utilisateur);

        return "Mot de passe réinitialisé avec succès.";
    }

}

