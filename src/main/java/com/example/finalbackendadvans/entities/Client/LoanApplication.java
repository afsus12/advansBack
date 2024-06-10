package com.example.finalbackendadvans.entities.Client;

import com.example.finalbackendadvans.entities.Staff;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nationalite;
    private int age;
    private String experienceDuration;
    private String loanPurpose;
    private String productName;
    private String governorat;
    private String activityAdresse;

    private String Fullname;
    private String Customer;
    private String activitytype;
    private  String previousMicrofinanceInstitution;
    private double requestedCreditAmount;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Date applicationDate;
    private Date createdAt;
    private Date validatedAt;

    @Lob
    private byte[] supportingDocument;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    @JsonBackReference
    private Staff staff;

    public enum Status {
            REFUSED, IN_PROGRESS, STEP1_VERIFIED, FULLY_VERIFIED
        }

        @PrePersist
        protected void onCreate() {
            createdAt = new Date();
        }

    }

