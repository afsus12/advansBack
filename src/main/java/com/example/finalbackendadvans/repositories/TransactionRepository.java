package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.Client.AdvansWallet;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.client.id = :clientId AND (:transtype IS NULL OR t.transtype = :transtype)")
    Page<Transaction> findByClientIdAndTranstype(
            @Param("clientId") Long clientId,
            @Param("transtype") Transaction.TransType transtype,
            Pageable pageable
    );
    Page<Transaction> findByClientId(Long clientId, Pageable pageable);
    Transaction getTransactionByClient(Client client);
}
