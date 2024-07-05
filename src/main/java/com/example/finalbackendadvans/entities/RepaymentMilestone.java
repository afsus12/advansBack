package com.example.finalbackendadvans.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
public class RepaymentMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getPaidOn() {
        return paidOn;
    }

    public void setPaidOn(LocalDate paidOn) {
        this.paidOn = paidOn;
    }

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate dueDate;

    private boolean paid;
    private LocalDate paidOn;

    // Constructors
    public RepaymentMilestone() {
    }

    public RepaymentMilestone(double amount, LocalDate dueDate) {
        this.amount = amount;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}