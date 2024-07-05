package com.example.finalbackendadvans.controllers;

import com.example.finalbackendadvans.services.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final FileStorageService fileStorageService;

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getDocument(@PathVariable String filename) {
        Resource file = (Resource) fileStorageService.loadFileAsResource(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Adjust the content type if necessary
                .body(file);
    }
}