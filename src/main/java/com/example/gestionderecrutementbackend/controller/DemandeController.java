package com.example.gestionderecrutementbackend.controller;
import com.example.gestionderecrutementbackend.Exception.BusinessLogicException;
import com.example.gestionderecrutementbackend.Exception.ResourceNotFoundException;
import com.example.gestionderecrutementbackend.dto.DemandeDTO;
import com.example.gestionderecrutementbackend.model.Demande;
import com.example.gestionderecrutementbackend.repository.DemandeRepository;
import com.example.gestionderecrutementbackend.service.DemandeService;
import com.example.gestionderecrutementbackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private DemandeRepository demandeRepository;


    @Value("${file.upload.dir}")
    private String uploadDir;

    @PostMapping("/postuler")
    public ResponseEntity<?> postuler(
            @RequestParam Long candidatId,
            @RequestParam Long offreId,
            @RequestParam String email,
            @RequestParam MultipartFile cv,
            @RequestParam MultipartFile lettreMotivation,
            @RequestParam MultipartFile diplome) {
        try {
            demandeService.creerDemande(candidatId, offreId, email, cv, lettreMotivation, diplome);
            return ResponseEntity.ok("Candidature envoyée avec succès !");
        } catch (IllegalArgumentException | ResourceNotFoundException | BusinessLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'enregistrement des fichiers.");
        }
    }


    @GetMapping("/fichiers/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            // Résolvez uniquement le nom de fichier
            Path filePath = Paths.get("C:/uploads").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private String determineContentType(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        return "application/octet-stream";  // Type générique pour les fichiers non reconnus
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemande(@PathVariable Long id) {
        Demande demande = demandeService.getDemandeById(id);

        // Générer les URLs pour les fichiers en utilisant des chemins relatifs
        String cvUrl = "/api/demandes/fichiers/" + demande.getCvUrl();
        String diplomeUrl = "/api/demandes/fichiers/" + demande.getDiplomeUrl();
        String lettreMotivationUrl = "/api/demandes/fichiers/" + demande.getLettreMotivationUrl();

        // Ajouter les URLs à l'objet Demande
        demande.setCvUrl(cvUrl);
        demande.setDiplomeUrl(diplomeUrl);
        demande.setLettreMotivationUrl(lettreMotivationUrl);

        return ResponseEntity.ok(demande);
    }



    // Accepter une demande
    @PostMapping("/{demandeId}/accepter")
    public ResponseEntity<?> accepterDemande(@PathVariable Long demandeId) {
        try {
            // Calculer la date d'entretien (une semaine après la date actuelle)
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 7);  // Ajouter 7 jours à la date actuelle
            Date entretienDate = calendar.getTime();  // Date d'entretien calculée

            // Accepter la demande avec la date d'entretien calculée
            Demande demande = demandeService.accepterDemande(demandeId, entretienDate);

            // Formater la date pour l'e-mail
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy à HH:mm", Locale.FRANCE);
            String formattedDate = dateFormat.format(entretienDate);

            // Construire le message de l'e-mail
            String message = String.format(
                    "Votre demande pour l'offre a été acceptée. Votre date d'entretien est : %s. Merci de vérifier vos emails pour plus d'informations.",
                    formattedDate
            );

            // Envoyer l'e-mail
            emailService.envoyerEmail(
                    demande.getEmail(),
                    "Demande acceptée",
                    message
            );

            // Retourner la réponse avec la demande acceptée
            return ResponseEntity.ok(demande);

        } catch (RuntimeException e) {
            // Gestion des erreurs spécifiques
            if (e.getMessage().contains("Demande introuvable")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demande introuvable.");
            } else if (e.getMessage().contains("La date d'entretien doit être dans le futur")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La date d'entretien doit être dans le futur.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    // Refuser une demande
    @PostMapping("/{demandeId}/refuser")
    public Demande refuserDemande(@PathVariable Long demandeId) {
        Demande demande = demandeService.refuserDemande(demandeId);  // Appeler le service pour refuser la demande
        // Envoyer un e-mail pour informer le candidat du refus
        emailService.envoyerEmail(
                demande.getEmail(),
                "Demande refusée",
                "Nous regrettons de vous informer que votre demande pour l'offre a été refusée."
        );
        return demande;  // Retourner l'objet demande mis à jour
    }

    @GetMapping("/{offreId}/demandes")
    public ResponseEntity<List<DemandeDTO>> getDemandesByOffre(@PathVariable Long offreId) {
        List<DemandeDTO> demandesDTO = demandeService.getDemandesByOffre(offreId);
        return ResponseEntity.ok(demandesDTO);
    }
    @GetMapping("/accepted-entretien")
    public ResponseEntity<List<Map<String, Object>>> getDemandesWithEntretiens() {
        List<Map<String, Object>> demandes = demandeService.getAcceptedDemandesWithEntretienDetails();
        return ResponseEntity.ok(demandes);
    }

}
