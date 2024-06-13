package com.example.finalbackendadvans.services;

import com.example.finalbackendadvans.dto.*;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.Status;
import com.example.finalbackendadvans.entities.Role;
import com.example.finalbackendadvans.entities.Staff;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import com.example.finalbackendadvans.repositories.AppUserRepository;
import com.example.finalbackendadvans.repositories.ClientRepository;
import com.example.finalbackendadvans.repositories.ProspectRepository;
import com.example.finalbackendadvans.repositories.StaffRepository;
import com.example.finalbackendadvans.security.config.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ClientRepository clientRepository;
    private  final StaffRepository staffRepository;
    private  final AppUserRepository appUserRepository;
    private  final ProspectRepository prospectRepository;

    private  final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;
    private  final JwtService jwtService;
    public LoginResponse authenticateStaff(LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password", e);
        }

        var user = appUserRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.getRole().equals(Role.ROLE_CC)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role");
        }

        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .jwt(jwtToken)
                .roles(user.getRole())
                .build();
    }

    public LoginResponse authenticateClient(LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password", e);
        }

        var user = clientRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!user.getRole().equals(Role.ROLE_CLIENT)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role");
        }

        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .jwt(jwtToken)
                .roles(user.getRole())
                .build();
    }

    public ResponseEntity<?> registerClient(ClientSignUpRequest signUpRequest){

        try {
            if(appUserRepository.existsByUsername(signUpRequest.getUsername())){
                return  ResponseEntity.badRequest().body("Username allready exists");
            }


        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password", e);
        }


        Prospect prospect = prospectRepository.findProspectByCustomer(signUpRequest.getCustomer());

        Client utilisateur = new Client();
        utilisateur.setStatus(Status.NOT_ACTIVE);
        utilisateur.setUsername( signUpRequest.getUsername());
        utilisateur.setEmail(prospect.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        utilisateur.setPhone( prospect.getPhoneNumber());
        utilisateur.setLastname(prospect.getLastname());
        utilisateur.setFirstname(prospect.getFirstname());
        utilisateur.setAddress(prospect.getAddress());
        utilisateur.setCin(prospect.getCin());
        utilisateur.setProspect(prospect);
        utilisateur.setBirthday(signUpRequest.getBirthdate());

        utilisateur.setRole( Role.ROLE_CLIENT);
        clientRepository.save(utilisateur);
        return  ResponseEntity.ok().body("User Created Successfully");
    }
    public ResponseEntity<?> registerStaff(CcSignUpRequest signUpRequest){
        if (appUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (appUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new admin's account
        Staff utilisateur = new Staff();
        utilisateur.setUsername( signUpRequest.getUsername());
        utilisateur.setEmail( signUpRequest.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        utilisateur.setPhone( signUpRequest.getPhone());
        utilisateur.setLastname( signUpRequest.getLastname());
        utilisateur.setFirstname( signUpRequest.getFirstname());
        utilisateur.setAddress( signUpRequest.getAddress());
        utilisateur.setRole( Role.ROLE_CC);
     staffRepository.save(utilisateur);
        return  ResponseEntity.ok().body("User Created Successfully");
    }
}
