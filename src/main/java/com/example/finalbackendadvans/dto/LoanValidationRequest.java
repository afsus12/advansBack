package com.example.finalbackendadvans.dto;

public class LoanValidationRequest {
    private double amount;
    private int nbrofmonths;
    private double interest;

    // Getters and setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNbrofmonths() {
        return nbrofmonths;
    }

    public void setNbrofmonths(int nbrofmonths) {
        this.nbrofmonths = nbrofmonths;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}