package com.example.gestionderecrutementbackend.dto;
public class PasswordResetRequest {
    private String resetToken;
    private String newPassword;

    // Getters et Setters
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
