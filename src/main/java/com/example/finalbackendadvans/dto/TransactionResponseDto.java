package com.example.finalbackendadvans.dto;



import com.example.finalbackendadvans.entities.Client.Transaction;

import java.time.LocalDateTime;

public class TransactionResponseDto {

    private Long id;
    private double amount;
    private Transaction.TransType transtype;
    private String type;
    private LocalDateTime date;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Transaction.TransType getTranstype() {
        return transtype;
    }

    public void setTranstype(Transaction.TransType transtype) {
        this.transtype = transtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
