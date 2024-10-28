package com.example.gestionderecrutementbackend.config;

import com.example.gestionderecrutementbackend.model.Admin;
import com.example.gestionderecrutementbackend.model.Recruteur;
import com.example.gestionderecrutementbackend.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultUserInitializer {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDefaultUsers() {
        return args -> {
            // Vérifier si l'admin existe déjà
            if (!utilisateurRepository.existsByEmail("admin@admin.com")) {
                Admin admin = new Admin();
                admin.setNom("Admin");
                admin.setPrenom("System");
                admin.setEmail("admin@admin.com");
                admin.setMotDePasse(passwordEncoder.encode("admin123"));
                // Ajouter d'autres détails si nécessaire
                utilisateurRepository.save(admin);
                System.out.println("Admin account created: admin@admin.com / admin123");
            }

            // Vérifier si le recruteur existe déjà
            if (!utilisateurRepository.existsByEmail("recruteur@company.com")) {
                Recruteur recruteur = new Recruteur();
                recruteur.setNom("Recruteur");
                recruteur.setPrenom("Company");
                recruteur.setEmail("recruteur@company.com");
                recruteur.setMotDePasse(passwordEncoder.encode("recruteur123"));
                // Ajouter d'autres détails si nécessaire
                utilisateurRepository.save(recruteur);
                System.out.println("Recruteur account created: recruteur@company.com / recruteur123");
            }
        };
    }
}
