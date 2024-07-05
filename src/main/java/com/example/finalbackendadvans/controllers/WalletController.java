package com.example.finalbackendadvans.controllers;

import com.example.finalbackendadvans.dto.MobileMoneyRequestDTO;
import com.example.finalbackendadvans.dto.PayMileStoneDTO;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.repositories.AdvansWalletRepository;
import com.example.finalbackendadvans.repositories.ClientRepository;
import com.example.finalbackendadvans.services.AdvansWalletService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client/wallet")
public class WalletController {

    private final AdvansWalletService walletService;
    private  final ClientRepository clientRepository;

    public WalletController(AdvansWalletService walletService,ClientRepository clientRepository) {
        this.walletService = walletService;
        this.clientRepository=clientRepository;
    }

    @PostMapping("/addBalance")
        public ResponseEntity<?> addBalanceToWallet(@RequestBody MobileMoneyRequestDTO request) {
        Client client = clientRepository.getClientById(request.getClientId()); // Retrieve client from repository or service
        try {
            walletService.addBalanceToWallet(client, request.getAmount() );
            return ResponseEntity.ok("Balance added successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
