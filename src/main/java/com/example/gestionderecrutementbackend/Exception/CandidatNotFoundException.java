package com.example.gestionderecrutementbackend.Exception;

public class CandidatNotFoundException extends RuntimeException {
    public CandidatNotFoundException(String message) {
        super(message);
    }
}