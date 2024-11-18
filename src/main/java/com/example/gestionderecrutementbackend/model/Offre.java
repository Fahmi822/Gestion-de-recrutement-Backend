package com.example.gestionderecrutementbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String post;
    private String niveau_etude;
    private String experience;
    private String langue;
    private int nb_a_recrut;
    private String ville;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date Dateexperation;
    private  Double Salaire;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    @JsonIgnore
    @ManyToMany(mappedBy = "offres")
    private List<Demande> demandes;
    @ManyToOne
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;
    // Getters and Setters
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



    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public String getNiveau_etude() {
        return niveau_etude;
    }

    public void setNiveau_etude(String niveau_etude) {
        this.niveau_etude = niveau_etude;
    }

    public String getExperince() {
        return experience;
    }

    public void setExperince(String experience) {
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

    public Date getDateexperation() {
        return Dateexperation;
    }

    public void setDateexperation(Date Dateexperation) {
        Dateexperation = Dateexperation;
    }

    public Double getSalaire() {
        return Salaire;
    }

    public void setSalaire(Double salaire) {
        Salaire = salaire;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }
}
