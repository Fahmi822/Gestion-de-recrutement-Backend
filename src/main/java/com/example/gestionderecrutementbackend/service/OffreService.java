package com.example.gestionderecrutementbackend.service;
import com.example.gestionderecrutementbackend.dto.OffreDTO;
import com.example.gestionderecrutementbackend.model.Offre;
import com.example.gestionderecrutementbackend.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreService {

    @Autowired
    private OffreRepository offreRepository;

    public Offre ajouterOffre(Offre offre) {
        return offreRepository.save(offre);
    }
    public OffreDTO convertToDTO(Offre offre) {
        return new OffreDTO(
                offre.getId(),
                offre.getPost(),
                offre.getNiveau_etude(),
                offre.getExperience(),
                offre.getLangue(),
                offre.getNb_a_recrut(),
                offre.getVille(),
                offre.getDateexperation(),
                offre.getSalaire(),
                offre.getDescription()
        );
    }

    public List<OffreDTO> obtenirToutesLesOffres() {
        List<Offre> offres = offreRepository.findAll();
        return offres.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Offre modifierOffre(Long id, Offre offre) {
        return offreRepository.findById(id).map(o -> {
            o.setPost(offre.getPost());
            o.setNiveau_etude(offre.getNiveau_etude());
            o.setExperince(offre.getExperince());
            o.setLangue(offre.getLangue());
            o.setNb_a_recrut(offre.getNb_a_recrut());
            o.setVille(offre.getVille());
            o.setDateexperation(offre.getDateexperation());
            o.setSalaire(offre.getSalaire());
            o.setDescription(offre.getDescription());
            return offreRepository.save(o);
        }).orElseThrow(() -> new RuntimeException("Offre non trouvée"));
    }

    public void supprimerOffre(Long id) {
        offreRepository.deleteById(id);
    }
    public long getTotalOffre() {
        return offreRepository.count();
    }

}