package com.example.gestionderecrutementbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageProperties {
    @Value("${uploads.directory}")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
}