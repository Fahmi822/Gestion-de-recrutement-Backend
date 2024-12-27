package com.example.gestionderecrutementbackend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status = "En Attente";
    private Date date;

    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat candidat;

    @ManyToMany
    @JoinTable(
            name = "demande_offre",
            joinColumns = @JoinColumn(name = "demande_id"),
            inverseJoinColumns = @JoinColumn(name = "offre_id")
    )
    private List<Offre> offres;
    @Column(nullable = false)
    private String email;
    private String cv;
    private String lettreMotivation;
    private String diplome;
    private String cvUrl;
    private String diplomeUrl;
    private String lettreMotivationUrl;

    public Demande() {
        this.status = "En Attente";  // Ensure default value
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruteur_id",referencedColumnName = "id")
    private Recruteur recruteur;

    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL)
    private Entretien entretien;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public List<Offre> getOffres() {
        return offres;
    }

    public void setOffres(List<Offre> offres) {
        this.offres = offres;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getLettreMotivation() {
        return lettreMotivation;
    }

    public void setLettreMotivation(String lettreMotivation) {
        this.lettreMotivation = lettreMotivation;
    }

    public Entretien getEntretien() {
        return entretien;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getDiplomeUrl() {
        return diplomeUrl;
    }

    public void setDiplomeUrl(String diplomeUrl) {
        this.diplomeUrl = diplomeUrl;
    }

    public String getLettreMotivationUrl() {
        return lettreMotivationUrl;
    }

    public void setLettreMotivationUrl(String lettreMotivationUrl) {
        this.lettreMotivationUrl = lettreMotivationUrl;
    }
}
