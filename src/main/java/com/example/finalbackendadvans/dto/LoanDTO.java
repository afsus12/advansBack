package com.example.finalbackendadvans.dto;

import java.time.LocalDate;

public class LoanDTO {
    private Long id;

    private double loanAmount;
    private double interestRate;
    private String productName;
    private int numberOfMonthsToRepay;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private double totalAmount;
    private String status;

    public double getPayedAmount() {
        return payedAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPayedAmount(double payedAmount) {
        this.payedAmount = payedAmount;
    }

    private double payedAmount;

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String loanType) {
        LoanType = loanType;
    }

    private String LoanType;

    // Constructor, getters, and setters

    public LoanDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getNumberOfMonthsToRepay() {
        return numberOfMonthsToRepay;
    }

    public void setNumberOfMonthsToRepay(int numberOfMonthsToRepay) {
        this.numberOfMonthsToRepay = numberOfMonthsToRepay;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}