package com.example.gestionderecrutementbackend.repository;

import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.model.Demande;
import com.example.gestionderecrutementbackend.model.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    boolean existsByCandidatAndOffresContains(Candidat candidat, Offre offre);
    List<Demande> findByOffresId(Long offreId);
    @Query("SELECT d.email, e.dateEntretien, e.status, o.post " +
            "FROM Demande d " +
            "JOIN d.entretien e " +
            "JOIN d.offres o " +
            "WHERE d.status = 'ACCEPTEE' AND e.dateEntretien IS NOT NULL")
    List<Object[]> findAcceptedDemandesWithEntretienAndPostDetails();


}
