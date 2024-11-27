package com.example.gestionderecrutementbackend.controller;
import com.example.gestionderecrutementbackend.dto.EmailRequest;
import com.example.gestionderecrutementbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private EmailService mailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        // Appel du service pour envoyer l'email
        mailService.envoyerEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        // Si l'email est envoyé avec succès
        return ResponseEntity.ok("Email envoyé avec succès.");
    }

}

