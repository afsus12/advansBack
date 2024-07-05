package com.example.finalbackendadvans.controllers;


import com.example.finalbackendadvans.dto.*;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.repositories.ClientRepository;
import com.example.finalbackendadvans.repositories.ProspectRepository;
import com.example.finalbackendadvans.services.AuthenticationService;
import com.example.finalbackendadvans.services.ProspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private  final AuthenticationService service;
    private  final ProspectService prospectService;
    @Autowired
    private ClientRepository clientRepository;
    @PostMapping("/cc/signin")
    public  ResponseEntity<?> authenticateStaff(@Valid @RequestBody LoginRequest loginRequest) {
        try {

        return ResponseEntity.ok(service.authenticateStaff(loginRequest));
    } catch (ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

    }
        @PostMapping("/client/signin")
    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {

        try {
        return ResponseEntity.ok(service.authenticateClient(loginRequest));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

        @PostMapping("/cc/signup")
    public ResponseEntity<?> registerStaff(@Valid @RequestBody CcSignUpRequest signUpRequest) {

        return ResponseEntity.ok(service.registerStaff(signUpRequest));

    }

    @PostMapping("/client/signup")
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClientSignUpRequest signUpRequest) {

        try {
            return ResponseEntity.ok(service.registerClient(signUpRequest));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }

    }

    @GetMapping("/cc/{customer}")
    public ResponseEntity<?> getProspect( @PathVariable String customer) {

        return prospectService.getProspectsByCustomerCode(customer);

    }


}
