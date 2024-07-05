package com.example.finalbackendadvans.dto;

public class MobileMoneyRequestDTO {
    private  long clientId;
    private  double amount;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
