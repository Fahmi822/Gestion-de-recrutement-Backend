package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.Exception.BusinessLogicException;
import com.example.gestionderecrutementbackend.Exception.ResourceNotFoundException;
import com.example.gestionderecrutementbackend.model.Demande;
import com.example.gestionderecrutementbackend.service.DemandeService;
import com.example.gestionderecrutementbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/postuler")
    public ResponseEntity<?> postuler(
            @RequestParam Long candidatId,
            @RequestParam Long offreId,
            @RequestParam String email,
            @RequestParam MultipartFile cv,
            @RequestParam MultipartFile lettreMotivation,
            @RequestParam MultipartFile diplome) {
        try {
            demandeService.creerDemande(candidatId, offreId, email, cv, lettreMotivation, diplome);
            return ResponseEntity.ok("Candidature envoyée avec succès !");
        } catch (IllegalArgumentException | ResourceNotFoundException | BusinessLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue.");
        }
    }
    @GetMapping("/recruteur/{recruteurId}")
    public List<Demande> getDemandesByRecruteur(@PathVariable Long recruteurId) {
        return demandeService.getDemandesByRecruteurId(recruteurId);
    }

    // Accepter une demande
    @PostMapping("/{demandeId}/accepter")
    public Demande accepterDemande(@PathVariable Long demandeId) {
        Demande demande = demandeService.accepterDemande(demandeId);
        emailService.envoyerEmail(
                demande.getEmail(),
                "Demande acceptée",
                "Votre demande pour l'offre a été acceptée. Merci de vérifier vos emails pour plus d'informations."
        );
        return demande;
    }

    // Refuser une demande
    @PostMapping("/{demandeId}/refuser")
    public Demande refuserDemande(@PathVariable Long demandeId) {
        Demande demande = demandeService.refuserDemande(demandeId);
        emailService.envoyerEmail(
                demande.getEmail(),
                "Demande refusée",
                "Nous regrettons de vous informer que votre demande pour l'offre a été refusée."
        );
        return demande;
    }
}

