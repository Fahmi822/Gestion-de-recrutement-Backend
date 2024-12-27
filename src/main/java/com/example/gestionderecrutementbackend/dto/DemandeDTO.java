package com.example.gestionderecrutementbackend.dto;

import java.util.Date;

public class DemandeDTO {
    private Long id;
    private String status;
    private Date date;
    private String email; // Email du candidat
    private String cv;
    private String lettreMotivation;
    private String diplome;

    // Constructeurs
    public DemandeDTO() {}

    public DemandeDTO(Long id, String status, Date date, String email, String cv, String lettreMotivation, String diplome) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.email = email;
        this.cv = cv;
        this.lettreMotivation = lettreMotivation;
        this.diplome = diplome;
    }

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

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }
}
