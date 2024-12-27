package com.example.gestionderecrutementbackend.repository;

import com.example.gestionderecrutementbackend.model.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntretienRepository extends JpaRepository<Entretien, Long> {
    Optional<Entretien> findByDemandeId(Long demandeId);
}
