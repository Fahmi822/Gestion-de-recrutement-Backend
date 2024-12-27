package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.Exception.CandidatNotFoundException;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.service.CandidatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CandidatController {

    @Autowired
    private CandidatService candidatService;

    @GetMapping("/candidats")
    public List<Candidat> getAllCandidates() {
        return candidatService.getAllCandidates();
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

    @PutMapping("/{id}/update-profile")
    public ResponseEntity<Candidat> updateProfile(@PathVariable Long id, @Valid @RequestBody Candidat updatedCandidat) {
        Candidat candidat = candidatService.updateCandidatProfile(id, updatedCandidat);
        return ResponseEntity.ok(candidat);
    }
    @GetMapping("/candidats/{id}")
    public ResponseEntity<Candidat> getCandidatById(@PathVariable Long id) {
        try {
            Candidat candidat = candidatService.getCandidatById(id);
            return ResponseEntity.ok(candidat);
        } catch (CandidatNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
