package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.LoanApplication;
import com.example.finalbackendadvans.entities.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
  List<LoanApplication> getAllByClient(Client client);
  @Query("SELECT la FROM LoanApplication la WHERE la.staff = :staff AND (:status IS NULL OR la.status = :status) ORDER BY la.applicationDate DESC")
  Page<LoanApplication> findAllByStaffAndStatus(@Param("staff") Staff staff, @Param("status") LoanApplication.Status status, Pageable pageable);
  @Query("SELECT la FROM LoanApplication la LEFT JOIN FETCH la.supportingDocumentPaths WHERE la.id = :id")
  Optional<LoanApplication> findByIdWithSupportingDocuments(@Param("id") Long id);


  @Query("SELECT la FROM LoanApplication la WHERE la.client = :client AND (:status IS NULL OR la.status = :status) ORDER BY la.applicationDate DESC")
  Page<LoanApplication> findAllByClientAndStatus(@Param("client") Client client, @Param("status") LoanApplication.Status status, Pageable pageable);
  List<LoanApplication> findByClient(Client client);
};


