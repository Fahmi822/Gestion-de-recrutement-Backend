package com.example.gestionderecrutementbackend.service;

import com.example.gestionderecrutementbackend.Exception.BusinessLogicException;
import com.example.gestionderecrutementbackend.Exception.ResourceNotFoundException;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.model.Demande;
import com.example.gestionderecrutementbackend.model.Offre;
import com.example.gestionderecrutementbackend.repository.CandidatRepository;
import com.example.gestionderecrutementbackend.repository.DemandeRepository;
import com.example.gestionderecrutementbackend.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private OffreRepository offreRepository;

    public void creerDemande(Long candidatId, Long offreId) {
        if (candidatId == null || offreId == null) {
            throw new IllegalArgumentException("Candidat ID et Offre ID ne peuvent pas être nuls");
        }

        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidat non trouvé"));

        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new ResourceNotFoundException("Offre non trouvée"));

        if (demandeRepository.existsByCandidatAndOffresContains(candidat, offre)) {
            throw new BusinessLogicException("Le candidat a déjà postulé pour cette offre.");
        }

        Demande demande = new Demande();
        demande.setDate(new Date());
        demande.setCandidat(candidat);
        demande.setOffres(Collections.singletonList(offre));
        demandeRepository.save(demande);
    }

}
