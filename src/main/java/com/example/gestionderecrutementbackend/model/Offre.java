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
}
