package com.example.gestionderecrutementbackend.service;

import com.example.gestionderecrutementbackend.model.Utilisateur;
import com.example.gestionderecrutementbackend.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Get all users
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    // Get a user by ID
    public Optional<Utilisateur> getUserById(Long id) {
        return utilisateurRepository.findById(id);
    }


    // Get a user by email
    public Optional<Utilisateur> getUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    // Update user details (for admin or profile updates)
    public Utilisateur updateUser(Long id, Utilisateur updatedUser) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // Update the user fields (you can add more fields based on your needs)
        Utilisateur user = existingUser.get();
        user.setNom(updatedUser.getNom());
        user.setPrenom(updatedUser.getPrenom());
        user.setEmail(updatedUser.getEmail());
        user.setAge(updatedUser.getAge());
        user.setTel(updatedUser.getTel());
        user.setPhoto(updatedUser.getPhoto());

        // Update password if necessary (e.g. for profile changes)
        if (updatedUser.getMotDePasse() != null && !updatedUser.getMotDePasse().isEmpty()) {
            user.setMotDePasse(updatedUser.getMotDePasse()); // Password should be encoded if updated
        }

        return utilisateurRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        utilisateurRepository.deleteById(id);
    }
}
