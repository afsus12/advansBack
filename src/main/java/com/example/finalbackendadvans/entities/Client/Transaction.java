package com.example.finalbackendadvans.entities.Client;

import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "[transaction]")
public class Transaction {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


    @Column(nullable = false)
    private double amount;

   private  String type;
   private  TransType transtype;
    @Column(name = "date")
   private LocalDateTime date;
    public enum TransType {
        IN,OUT
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public TransType getTranstype() {
        return transtype;
    }

    public void setTranstype(TransType transtype) {
        this.transtype = transtype;
    }


}
