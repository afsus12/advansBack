package com.example.finalbackendadvans.dto;


import com.example.finalbackendadvans.entities.Client.LoanApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {
    private Long id;
    private LoanApplication.Status status;
    private String customer;
    private double requestedCreditAmount;
    private Date applicationDate;
    private String Fullname;
    private String productName;
}
