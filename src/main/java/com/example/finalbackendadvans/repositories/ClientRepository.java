package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.AppUser;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.LoanApplication;
import com.example.finalbackendadvans.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;


import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByUsername(String username);

 }
