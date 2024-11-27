package com.example.gestionderecrutementbackend.repository;

import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.model.Demande;
import com.example.gestionderecrutementbackend.model.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    boolean existsByCandidatAndOffresContains(Candidat candidat, Offre offre);
    List<Demande> findByRecruteurId(Long recruteurId);

}
