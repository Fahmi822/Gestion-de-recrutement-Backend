package com.example.gestionderecrutementbackend.dto;
public class LoginResponse {

    private String message;
    private String token;
    private String role;
    private Long utilisateurId;  // Ajout de l'ID de l'utilisateur

    // Constructeur avec ID de l'utilisateur
    public LoginResponse(String message, String token, String role, Long utilisateurId) {
        this.message = message;
        this.token = token;
        this.role = role;
        this.utilisateurId = utilisateurId;
    }

    // Getters et setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}
