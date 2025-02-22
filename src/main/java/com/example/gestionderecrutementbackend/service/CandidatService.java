package com.example.gestionderecrutementbackend.service;

import com.example.gestionderecrutementbackend.Exception.CandidatNotFoundException;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.repository.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
@Service
public class CandidatService {

    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Candidat> getAllCandidates() {
        return candidatRepository.findAll(); // Récupère tous les candidats
    }
    public long getTotalCandidates() {
        return candidatRepository.count();
    }

    // Récupérer le nombre d'hommes
    public long getNumberOfMen() {
        return candidatRepository.countByGenre("Homme");
    }

    // Récupérer le nombre de femmes
    public long getNumberOfWomen() {
        return candidatRepository.countByGenre("Femme");
    }
    public Candidat updateCandidatProfile(Long id, Candidat updatedCandidat) {
        return candidatRepository.findById(id).map(candidat -> {
            candidat.setNom(updatedCandidat.getNom());
            candidat.setPrenom(updatedCandidat.getPrenom());
            candidat.setEmail(updatedCandidat.getEmail());
            candidat.setTel(updatedCandidat.getTel());
            candidat.setAdresse(updatedCandidat.getAdresse());
            candidat.setGenre(updatedCandidat.getGenre());
            candidat.setPhoto(updatedCandidat.getPhoto());
            if (updatedCandidat.getMotDePasse() != null && !updatedCandidat.getMotDePasse().isBlank()) {
                // Hacher le nouveau mot de passe
                String hashedPassword = passwordEncoder.encode(updatedCandidat.getMotDePasse());
                candidat.setMotDePasse(hashedPassword);
            }
            return candidatRepository.save(candidat);
        }).orElseThrow(() -> new RuntimeException("Candidat not found with id: " + id));
    }
    public Candidat getCandidatById(Long id) {
        return candidatRepository.findById(id)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat not found with ID: " + id));
    }
}