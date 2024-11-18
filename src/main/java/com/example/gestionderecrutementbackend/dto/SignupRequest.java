package com.example.gestionderecrutementbackend.dto;


public class SignupRequest {
    private String nom;
    private String prenom;
    private int age;
    private String email;
    private String tel;
    private String photo;
    private String motDePasse;
    private String adresse;
    private String cv;
    private String lettreMotivation;
    private String diplome;
    private String genre;
    public SignupRequest() {
    }

    public SignupRequest(String nom, String prenom, int age, String email, String tel, String photo, String motDePasse,
                         String adresse, String cv, String lettreMotivation, String diplome,String genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.tel = tel;
        this.photo = photo;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        this.cv = cv;
        this.lettreMotivation = lettreMotivation;
        this.diplome = diplome;
        this.genre=genre;
    }


    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
