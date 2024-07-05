package com.example.finalbackendadvans.entities.Client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
public class AdvansWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Size(max = 14)
    @Column(nullable = false, unique = true, length = 14)
    private String accountNumber;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private String currency;

    // Constructors
    public AdvansWallet() {
        this.currency = "TND"; // Default currency is Tunisian Dinar
    }

    public AdvansWallet(Client client, String accountNumber, double balance) {
        this.client = client;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = "TND"; // Default currency is Tunisian Dinar
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}