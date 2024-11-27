package com.example.gestionderecrutementbackend.Exception;

public class BusinessLogicException extends RuntimeException {

    // Constructeur avec uniquement un message
    public BusinessLogicException(String message) {
        super(message);
    }

    // Constructeur avec un message et une cause
    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
