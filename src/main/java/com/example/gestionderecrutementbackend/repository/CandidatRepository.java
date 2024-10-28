package com.example.gestionderecrutementbackend.repository;

import com.example.gestionderecrutementbackend.model.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    Candidat findByEmail(String email);

}