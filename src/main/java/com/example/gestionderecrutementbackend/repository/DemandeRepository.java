package com.example.gestionderecrutementbackend.repository;

import com.example.gestionderecrutementbackend.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
}
