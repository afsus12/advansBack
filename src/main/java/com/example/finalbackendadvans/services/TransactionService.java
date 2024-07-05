package com.example.finalbackendadvans.services;


import com.example.finalbackendadvans.dto.TransactionResponseDto;
import com.example.finalbackendadvans.entities.Client.Transaction;
import com.example.finalbackendadvans.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Page<TransactionResponseDto> getClientTransactions(Long clientId, Transaction.TransType transType, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findByClientIdAndTranstype(clientId, transType, pageable);
        return transactions.map(this::convertToDto);
    }

    private TransactionResponseDto convertToDto(Transaction transaction) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setTranstype(transaction.getTranstype());
        dto.setType(transaction.getType());
        dto.setDate(transaction.getDate());
        return dto;
    }
}