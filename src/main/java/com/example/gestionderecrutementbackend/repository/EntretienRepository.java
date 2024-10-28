package com.example.gestionderecrutementbackend.repository;

import com.example.gestionderecrutementbackend.model.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntretienRepository extends JpaRepository<Entretien, Long> {
}
