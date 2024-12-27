package com.example.gestionderecrutementbackend.dto;

import java.util.Date;

public class OffreDTO {
    private Long id;
    private String post;
    private String niveau_etude;
    private String experience;
    private String langue;
    private int nb_a_recrut;
    private String ville;
    private Date Dateexperation;
    private Double salaire;
    private String description;
    private Long admin_id; // ID de l'Admin
    private Long recruteur_id; // ID du Recruteur

    // Constructeur par défaut (nécessaire pour certains frameworks comme Hibernate ou Jackson)
    public OffreDTO() {
    }

    // Constructeur avec tous les paramètres
    public OffreDTO(Long id, String post, String niveau_etude, String experience, String langue, int nb_a_recrut,
                    String ville, Date Dateexperation, Double salaire, String description) {
        this.id = id;
        this.post = post;
        this.niveau_etude = niveau_etude;
        this.experience = experience;
        this.langue = langue;
        this.nb_a_recrut = nb_a_recrut;
        this.ville = ville;
        this.Dateexperation = Dateexperation;
        this.salaire = salaire;
        this.description = description;

    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNiveau_etude() {
        return niveau_etude;
    }

    public void setNiveau_etude(String niveau_etude) {
        this.niveau_etude = niveau_etude;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getNb_a_recrut() {
        return nb_a_recrut;
    }

    public void setNb_a_recrut(int nb_a_recrut) {
        this.nb_a_recrut = nb_a_recrut;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Date getDate_expiration() {
        return Dateexperation;
    }

    public void setDate_expiration(Date Dateexperation) {
        this.Dateexperation = Dateexperation;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
