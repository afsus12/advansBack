package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.AppUser;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findByClientAndStatus(Client client, Loan.Status status);
    Loan getLoanById(Long id);
}
