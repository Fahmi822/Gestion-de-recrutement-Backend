package com.example.gestionderecrutementbackend.repository;

import com.example.gestionderecrutementbackend.model.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatRepository extends JpaRepository<Candidat, Long> {

    long countByGenre(String genre);
    Optional<Candidat> findByEmail(String email);
    Optional<Candidat> findByResetToken(String resetToken);




}