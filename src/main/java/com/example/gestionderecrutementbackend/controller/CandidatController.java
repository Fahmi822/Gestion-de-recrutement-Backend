package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.service.CandidatService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
