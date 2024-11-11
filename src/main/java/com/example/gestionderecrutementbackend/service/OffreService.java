package com.example.gestionderecrutementbackend.service;
import com.example.gestionderecrutementbackend.model.Offre;
import com.example.gestionderecrutementbackend.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffreService {

    @Autowired
    private OffreRepository offreRepository;

    public Offre ajouterOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    public List<Offre> obtenirToutesLesOffres() {
        return offreRepository.findAll();
    }

    public Offre modifierOffre(Long id, Offre offre) {
        return offreRepository.findById(id).map(o -> {
            o.setPost(offre.getPost());
            o.setNiveau_etude(offre.getNiveau_etude());
            o.setExperince(offre.getExperince());
            o.setLangue(offre.getLangue());
            o.setNb_a_recruté(offre.getNb_a_recruté());
            o.setVille(offre.getVille());
            o.setDate_experation(offre.getDate_experation());
            o.setSalaire(offre.getSalaire());
            return offreRepository.save(o);
        }).orElseThrow(() -> new RuntimeException("Offre non trouvée"));
    }

    public void supprimerOffre(Long id) {
        offreRepository.deleteById(id);
    }
}