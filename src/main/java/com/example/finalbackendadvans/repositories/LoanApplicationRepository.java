package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.LoanApplication;
import com.example.finalbackendadvans.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
  List<LoanApplication> getAllByClient(Client client);
  List<LoanApplication> getAllByStaff(Staff staff);
}

