package com.example.finalbackendadvans.entities.Client;

import com.example.finalbackendadvans.entities.AppUser;
import com.example.finalbackendadvans.entities.Loan;
import com.example.finalbackendadvans.entities.Message;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Client extends AppUser {

    private String cin;
    private Status status;
    @OneToOne
    @JoinColumn(name = "prospect_id")
    private Prospect prospect;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanApplication> loanApplications;
    private  Date birthday;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private AdvansWallet advansWallet;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}
