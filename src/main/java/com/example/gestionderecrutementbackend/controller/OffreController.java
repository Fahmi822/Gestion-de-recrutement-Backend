package com.example.gestionderecrutementbackend.controller;

import com.example.gestionderecrutementbackend.dto.OffreDTO;
import com.example.gestionderecrutementbackend.model.Offre;
import com.example.gestionderecrutementbackend.service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/offres")
public class OffreController {

    @Autowired
    private OffreService offreService;
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter")
    public ResponseEntity<Offre> ajouterOffre(@RequestBody Offre offre) {
        Offre nouvelleOffre = offreService.ajouterOffre(offre);
        return ResponseEntity.ok(nouvelleOffre);
    }

    @GetMapping("/toutes")
    public ResponseEntity<List<OffreDTO>> obtenirToutesLesOffres() {
        List<OffreDTO> offresDTO = offreService.obtenirToutesLesOffres();
        return ResponseEntity.ok(offresDTO);
    }


    @PutMapping("/modifier/{id}")
    public ResponseEntity<Offre> modifierOffre(@PathVariable Long id, @RequestBody Offre offre) {
        Offre offreModifiee = offreService.modifierOffre(id, offre);
        return ResponseEntity.ok(offreModifiee);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerOffre(@PathVariable Long id) {
        offreService.supprimerOffre(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalOffreCount() {
        long totalCount = offreService.getTotalOffre();
        return ResponseEntity.ok(totalCount);
    }
}