package com.example.finalbackendadvans.controllers;

import com.example.finalbackendadvans.dto.TransactionResponseDto;
import com.example.finalbackendadvans.entities.Client.Transaction;
import com.example.finalbackendadvans.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{clientId}")
    public Page<TransactionResponseDto> getClientTransactions(
            @PathVariable Long clientId,
            @RequestParam(required = false) Transaction.TransType transtype,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date,desc") String[] sort) { // Corrected default sort parameter

        Sort.Order order = new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        return transactionService.getClientTransactions(clientId, transtype, pageable);
    }
}
