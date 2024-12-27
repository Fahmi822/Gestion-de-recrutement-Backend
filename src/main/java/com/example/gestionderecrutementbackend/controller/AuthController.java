package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.dto.PasswordResetRequest;
import com.example.gestionderecrutementbackend.service.AuthService;
import com.example.gestionderecrutementbackend.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    public AuthController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        try {
            String resetToken = authService.generatePasswordResetToken(email);
            emailService.sendResetPasswordEmail(email, resetToken);
            return ResponseEntity.ok("Un email de réinitialisation a été envoyé.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest resetRequest) {
        try {
            // Vérification de la structure des données reçues
            if (resetRequest.getResetToken() == null || resetRequest.getResetToken().isEmpty()) {
                throw new IllegalArgumentException("Le token de réinitialisation est manquant.");
            }
            if (resetRequest.getNewPassword() == null || resetRequest.getNewPassword().isEmpty()) {
                throw new IllegalArgumentException("Le nouveau mot de passe est manquant.");
            }

            // Logique pour réinitialiser le mot de passe
            String resetToken = resetRequest.getResetToken();
            String newPassword = resetRequest.getNewPassword();
            String message = authService.resetPassword(resetToken, newPassword);

            // Réponse de succès
            return ResponseEntity.ok(message);

        } catch (IllegalArgumentException e) {
            // Retourner une erreur 400 pour une requête mal formée
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur: " + e.getMessage());

        } catch (RuntimeException e) {
            // Gestion générique des erreurs
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Une erreur est survenue: " + e.getMessage());
        }
    }





}
