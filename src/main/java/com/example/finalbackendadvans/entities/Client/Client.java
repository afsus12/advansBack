package com.example.finalbackendadvans.entities.Client;

import com.example.finalbackendadvans.entities.AppUser;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Client extends AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
private String cin;
private Status status;
    @OneToOne
    @JoinColumn(name = "prospect_id")
    private Prospect prospect;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanApplication> loanApplications;
    private  Date birthday;



}
