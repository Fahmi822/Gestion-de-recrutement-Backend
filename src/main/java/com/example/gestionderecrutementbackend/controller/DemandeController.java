package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.Exception.BusinessLogicException;
import com.example.gestionderecrutementbackend.Exception.ResourceNotFoundException;
import com.example.gestionderecrutementbackend.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @PostMapping("/postuler")
    public ResponseEntity<?> postuler(@RequestParam Long candidatId, @RequestParam Long offreId) {
        try {
            demandeService.creerDemande(candidatId, offreId);
            return ResponseEntity.ok("Candidature envoyée avec succès !");
        } catch (IllegalArgumentException | ResourceNotFoundException | BusinessLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue.");
        }
    }

}
