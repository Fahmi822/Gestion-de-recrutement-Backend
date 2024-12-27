package com.example.gestionderecrutementbackend.service;
import com.example.gestionderecrutementbackend.Exception.BusinessLogicException;
import com.example.gestionderecrutementbackend.Exception.ResourceNotFoundException;
import com.example.gestionderecrutementbackend.dto.DemandeDTO;
import com.example.gestionderecrutementbackend.model.Candidat;
import com.example.gestionderecrutementbackend.model.Demande;
import com.example.gestionderecrutementbackend.model.Entretien;
import com.example.gestionderecrutementbackend.model.Offre;
import com.example.gestionderecrutementbackend.repository.CandidatRepository;
import com.example.gestionderecrutementbackend.repository.DemandeRepository;
import com.example.gestionderecrutementbackend.repository.OffreRepository;
import com.example.gestionderecrutementbackend.repository.EntretienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private EntretienRepository entretienRepository;
    @Value("${file.upload.dir}")
    private String uploadDir;

    public DemandeDTO convertToDTO(Demande demande) {
        return new DemandeDTO(
                demande.getId(),
                demande.getStatus(),
                demande.getDate(),
                demande.getCandidat().getEmail(), // Récupération de l'email du candidat
                demande.getCv(),
                demande.getLettreMotivation(),
                demande.getDiplome()
        );
    }
    public void creerDemande(Long candidatId, Long offreId, String email, MultipartFile cv, MultipartFile lettreMotivation, MultipartFile diplome) throws IOException {
        if (candidatId == null || offreId == null) {
            throw new IllegalArgumentException("Candidat ID et Offre ID ne peuvent pas être nuls.");
        }

        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidat non trouvé."));
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new ResourceNotFoundException("Offre non trouvée."));

        if (demandeRepository.existsByCandidatAndOffresContains(candidat, offre)) {
            throw new BusinessLogicException("Le candidat a déjà postulé pour cette offre.");
        }

        // Sauvegarde des fichiers
        String cvPath = saveFile(cv, "cv_" + candidatId + "_" + offreId);
        String lettreMotivationPath = saveFile(lettreMotivation, "lettre_" + candidatId + "_" + offreId);
        String diplomePath = saveFile(diplome, "diplome_" + candidatId + "_" + offreId);

        // Génération des URLs d'accès
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String cvUrl = baseUrl + "/api/demandes/fichiers/" + Paths.get(cvPath).getFileName().toString();
        String lettreMotivationUrl = baseUrl + "/api/demandes/fichiers/" + Paths.get(lettreMotivationPath).getFileName().toString();
        String diplomeUrl = baseUrl + "/api/demandes/fichiers/" + Paths.get(diplomePath).getFileName().toString();

        // Création de la demande
        Demande demande = new Demande();
        demande.setCandidat(candidat);
        demande.setOffres(Collections.singletonList(offre));
        demande.setEmail(email);
        demande.setCv(cvPath); // Chemin interne (au niveau du stockage)
        demande.setCvUrl(cvUrl); // URL d'accès (qui sera envoyée au frontend)
        demande.setLettreMotivation(lettreMotivationPath);
        demande.setLettreMotivationUrl(lettreMotivationUrl);
        demande.setDiplome(diplomePath);
        demande.setDiplomeUrl(diplomeUrl);
        demande.setDate(new Date());

        demandeRepository.save(demande);
    }

    private String saveFile(MultipartFile file, String fileName) throws IOException {
        // Vérifier si le fichier est nul ou vide
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier ne peut pas être vide.");
        }

        // Vérification de l'extension et du type MIME
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Seuls les fichiers PDF sont autorisés (extension .pdf).");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Le fichier n'est pas un PDF valide (type MIME incorrect).");
        }

        // Ajouter l'extension .pdf si elle est manquante dans le nom généré
        if (!fileName.endsWith(".pdf")) {
            fileName += ".pdf";
        }

        // Préparer le chemin de sauvegarde
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.createDirectories(filePath.getParent()); // Créer les dossiers si nécessaire

        // Sauvegarde du fichier
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Erreur lors de la sauvegarde du fichier : " + fileName, e);
        }

        // Retourne le chemin du fichier sauvegardé
        return filePath.toString();
    }
    
    public Demande getDemandeById(Long demandeId) {
        return demandeRepository.findById(demandeId)
                .orElseThrow(() -> new ResourceNotFoundException("Demande non trouvée."));
    }

    // Accepter une demande
    public Demande accepterDemande(Long demandeId, Date entretienDate) {
        // Récupérer la demande
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        // Vérifier si la demande est déjà acceptée
        if ("ACCEPTEE".equalsIgnoreCase(demande.getStatus())) {
            throw new BusinessLogicException("La demande est déjà acceptée !");
        }

        // Mettre à jour le statut de la demande
        demande.setStatus("ACCEPTEE");
        Demande demandeSauvegardee = demandeRepository.save(demande);

        // Vérifier si l'entretien existe déjà
        Entretien entretien = demandeSauvegardee.getEntretien();
        if (entretien == null) {
            // Si l'entretien n'existe pas, on le crée
            entretien = new Entretien();
            entretien.setDemande(demandeSauvegardee);
        }

        // Vérifier que la date d'entretien est au moins une semaine après la date actuelle
        if (entretienDate.before(new Date())) {
            throw new RuntimeException("La date d'entretien doit être dans le futur.");
        }

        // Définir la date de l'entretien
        entretien.setDateEntretien(entretienDate);

        // Définir le statut de l'entretien
        entretien.setStatus("PREVU");

        // Sauvegarder l'entretien
        entretienRepository.save(entretien);

        return demandeSauvegardee;
    }
    // Refuser une demande
    public Demande refuserDemande(Long demandeId) {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        demande.setStatus("REFUSEE");  // Mettre à jour le statut de la demande en "REFUSEE"
        return demandeRepository.save(demande);  // Sauvegarder la demande avec le nouveau statut
    }

    public List<Demande> getDemandesByOffreId(Long offreId) {
        // Récupérer l'offre spécifique
        Offre offre = offreRepository.findById(offreId).orElseThrow(() -> new RuntimeException("Offre non trouvée"));

        // Retourner toutes les demandes associées à cette offre
        return offre.getDemandes();

    }
    public List<DemandeDTO> getDemandesByOffre(Long offreId) {
        List<Demande> demandes = demandeRepository.findByOffresId(offreId);
        return demandes.stream()
                .map(this::convertToDTO) // Convertir chaque demande en DemandeDTO
                .toList();
    }
    public List<Map<String, Object>> getAcceptedDemandesWithEntretienDetails() {
        List<Object[]> results = demandeRepository.findAcceptedDemandesWithEntretienAndPostDetails();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("email", row[0]);
            map.put("dateEntretien", row[1]);
            map.put("status", row[2]);  // Statut de l'entretien
            map.put("post", row[3]);  // Nom du poste
            response.add(map);
        }
        return response;
    }





}
