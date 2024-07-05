package com.example.finalbackendadvans.repositories;


import com.example.finalbackendadvans.entities.Client.AdvansWallet;
import com.example.finalbackendadvans.entities.Client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvansWalletRepository extends JpaRepository<AdvansWallet,Long> {
    Optional<AdvansWallet> findByClient(Client client);
    AdvansWallet getAdvansWalletByClient(Client client);
}
