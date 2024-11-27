package com.example.gestionderecrutementbackend.service;
import com.example.gestionderecrutementbackend.Exception.BusinessLogicException;
import com.example.gestionderecrutementbackend.Exception.ResourceNotFoundException;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.model.Demande;
import com.example.gestionderecrutementbackend.model.Offre;
import com.example.gestionderecrutementbackend.repository.CandidatRepository;
import com.example.gestionderecrutementbackend.repository.DemandeRepository;
import com.example.gestionderecrutementbackend.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private OffreRepository offreRepository;

    public void creerDemande(Long candidatId, Long offreId, String email, MultipartFile cv, MultipartFile lettreMotivation, MultipartFile diplome) {
        if (candidatId == null || offreId == null) {
            throw new IllegalArgumentException("Candidat ID et Offre ID ne peuvent pas être nuls");
        }

        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidat non trouvé"));

        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new ResourceNotFoundException("Offre non trouvée"));

        if (demandeRepository.existsByCandidatAndOffresContains(candidat, offre)) {
            throw new BusinessLogicException("Le candidat a déjà postulé pour cette offre.");
        }

        String cvPath = saveFile(cv, "cv_" + candidatId + "_" + offreId);
        String lettreMotivationPath = saveFile(lettreMotivation, "lettre_" + candidatId + "_" + offreId);
        String diplomePath = saveFile(diplome, "diplome_" + candidatId + "_" + offreId);

        Demande demande = new Demande();
        demande.setDate(new Date());
        demande.setCandidat(candidat);
        demande.setOffres(Collections.singletonList(offre));
        demande.setEmail(email);
        demande.setCv(cvPath);
        demande.setLettreMotivation(lettreMotivationPath);
        demande.setDiplome(diplomePath);

        demandeRepository.save(demande);
    }

    private String saveFile(MultipartFile file, String filename) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Le fichier " + filename + " est vide !");
            }

            if (!file.getContentType().equals("application/pdf")) {
                throw new IllegalArgumentException("Le fichier doit être un PDF.");
            }

            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new BusinessLogicException("Erreur lors de l'enregistrement du fichier : " + filename, e);
        }
    }
    public List<Demande> getDemandesByRecruteurId(Long recruteurId) {
        return demandeRepository.findByRecruteurId(recruteurId);
    }

    // Accepter une demande
    public Demande accepterDemande(Long demandeId) {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        demande.setStatus("ACCEPTEE");
        return demandeRepository.save(demande);
    }

    // Refuser une demande
    public Demande refuserDemande(Long demandeId) {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        demande.setStatus("REFUSEE");
        return demandeRepository.save(demande);
    }
}
