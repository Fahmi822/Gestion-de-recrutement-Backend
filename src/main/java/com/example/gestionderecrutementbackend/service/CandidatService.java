package com.example.gestionderecrutementbackend.service;

import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.repository.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CandidatService {

    @Autowired
    private CandidatRepository candidatRepository;

    public List<Candidat> getAllCandidates() {
        return candidatRepository.findAll(); // Récupère tous les candidats
    }
}