package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.service.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api") // Sets the base path for the controller
public class CandidatController {

    @Autowired
    private CandidatService candidatService;

    @GetMapping("/candidats")
    public List<Candidat> getAllCandidates() {
        return candidatService.getAllCandidates(); // Retrieves all candidates
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCandidatesCount() {
        long totalCount = candidatService.getTotalCandidates();
        return ResponseEntity.ok(totalCount);
    }

    // Endpoint pour récupérer le nombre d'hommes
    @GetMapping("/count/men")
    public ResponseEntity<Long> getNumberOfMen() {
        long numberOfMen = candidatService.getNumberOfMen();
        return ResponseEntity.ok(numberOfMen);
    }

    // Endpoint pour récupérer le nombre de femmes
    @GetMapping("/count/women")
    public ResponseEntity<Long> getNumberOfWomen() {
        long numberOfWomen = candidatService.getNumberOfWomen();
        return ResponseEntity.ok(numberOfWomen);
    }
}
