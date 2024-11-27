package com.example.gestionderecrutementbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerEmail(String to, String sujet, String contenu) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(sujet);
        message.setText(contenu);
        message.setFrom("fahmihlel822@gmail.com");
        mailSender.send(message);
    }
}
