package com.example.finalbackendadvans.entities;

import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.LoanApplication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private Double loanAmount;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepaymentMilestone> repaymentMilestones = new ArrayList<>();

    private Double interestRate;

    private Integer numberOfMonthsToRepay;

    private LocalDate loanDate;

    private double payedAmount = 0.0;
    private double totalAmount; // Total

    public double getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(double payedAmount) {
        this.payedAmount = payedAmount;
    }

    private LocalDate dueDate;

    // Other attributes (example)
    private LocalDate validatedDate;

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Constructors
    public Loan() {
    }

    public Loan(double loanAmount, double interestRate, Integer numberOfMonthsToRepay, LocalDate loanDate, LocalDate dueDate) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.numberOfMonthsToRepay = numberOfMonthsToRepay;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    // Methods (example)
    public void addRepaymentMilestone(RepaymentMilestone milestone) {
        this.repaymentMilestones.add(milestone);
        milestone.setLoan(this);
    }

    public double calculateInterest() {
        // Example: Calculate interest based on loan amount, rate, and months
        double monthlyInterestRate = interestRate / 12.0;
        double totalInterest = loanAmount * monthlyInterestRate * numberOfMonthsToRepay;
        return Math.round(totalInterest * 100.0) / 100.0; // Round to 2 decimal places
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public List<RepaymentMilestone> getRepaymentMilestones() {
        return repaymentMilestones;
    }

    public void setRepaymentMilestones(List<RepaymentMilestone> repaymentMilestones) {
        this.repaymentMilestones = repaymentMilestones;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getNumberOfMonthsToRepay() {
        return numberOfMonthsToRepay;
    }

    public void setNumberOfMonthsToRepay(Integer numberOfMonthsToRepay) {
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

    public LocalDate getValidatedDate() {
        return validatedDate;
    }
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setValidatedDate(LocalDate validatedDate) {
        this.validatedDate = validatedDate;
    }
    public enum Status {
        IN_PROGRESS,
        FULLY_PAID,
        LATE
    }

}