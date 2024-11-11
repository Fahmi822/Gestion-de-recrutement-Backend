package com.example.gestionderecrutementbackend.model;

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
    private String experince;
    private String langue;
    private int nb_a_recruté;
    private String ville;
    private Date Date_experation;
    private  Long Salaire;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

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
        return experince;
    }

    public void setExperince(String experince) {
        this.experince = experince;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getNb_a_recruté() {
        return nb_a_recruté;
    }

    public void setNb_a_recruté(int nb_a_recruté) {
        this.nb_a_recruté = nb_a_recruté;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Date getDate_experation() {
        return Date_experation;
    }

    public void setDate_experation(Date date_experation) {
        Date_experation = date_experation;
    }

    public Long getSalaire() {
        return Salaire;
    }

    public void setSalaire(Long salaire) {
        Salaire = salaire;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }
}
