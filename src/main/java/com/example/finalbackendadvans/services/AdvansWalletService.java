package com.example.finalbackendadvans.services;

import com.example.finalbackendadvans.entities.Client.AdvansWallet;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.Transaction;
import com.example.finalbackendadvans.repositories.AdvansWalletRepository;
import com.example.finalbackendadvans.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdvansWalletService {

    private final AdvansWalletRepository walletRepository;
    private  final TransactionRepository transactionRepository;

    public AdvansWalletService(AdvansWalletRepository walletRepository,TransactionRepository transactionRepository ) {
        this.walletRepository = walletRepository;
        this.transactionRepository=transactionRepository;
    }




    @Transactional
    public void addBalanceToWallet(Client client, double amountToAdd) {
        AdvansWallet wallet = walletRepository.getAdvansWalletByClient(client);
        if (wallet != null) {
            double currentBalance = wallet.getBalance();
            double newBalance = currentBalance + amountToAdd;


            wallet.setBalance(newBalance);
            walletRepository.save(wallet);
            Transaction transaction= new Transaction();
            transaction.setClient(client);
            transaction.setAmount(amountToAdd);
            transaction.setDate(LocalDateTime.now());
            transaction.setType("Mobile Money Balance");
            transaction.setTranstype(Transaction.TransType.IN);
            transactionRepository.save(transaction);
        } else {
            // Handle case where wallet is not found
            throw new EntityNotFoundException("Wallet not found for client: " + client.getId());
        }
    }
}