package com.example.gestionderecrutementbackend.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

    public class PasswordEncoder {
        public static void main(String[] args) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String rawPassword = "adminPassword"; // Replace this with your desired password
            String hashedPassword = passwordEncoder.encode(rawPassword);
            System.out.println(hashedPassword); // Print the hashed password
        }
    }

