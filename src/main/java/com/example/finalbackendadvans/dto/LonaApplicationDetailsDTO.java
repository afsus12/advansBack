package com.example.finalbackendadvans.dto;

import com.example.finalbackendadvans.entities.Client.LoanApplication;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LonaApplicationDetailsDTO {
    private Long id;
    private LoanApplication.Status status;
    private String customer;
    private double requestedCreditAmount;
    private Date applicationDate;
    private String Fullname;
    private String nationalite;
    private int age;
    private String experienceDuration;
    private String loanPurpose;
    private String productName;
    private String governorat;
    private String activityAdresse;


    private String activitytype;


    private Date createdAt;
    private Date validatedAt;

    @Lob
    private byte[] supportingDocument;


    public enum Status {
        REFUSED, IN_PROGRESS, STEP1_VERIFIED, FULLY_VERIFIED
    }

}
